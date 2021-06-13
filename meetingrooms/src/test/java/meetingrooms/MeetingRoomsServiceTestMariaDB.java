package meetingrooms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeetingRoomsServiceTestMariaDB {

    static MeetingRoomsService meetingRoomsService;

    @BeforeAll
    static void newMeetingRoom() {
        MeetingRoomsRepository meetingRoomsRepository = new MariaDbMeetingRoomsRepository();
        meetingRoomsService = new MeetingRoomsService(meetingRoomsRepository);
        meetingRoomsRepository.deleteAll();

        meetingRoomsService.newMeetingRoom(new MeetingRoom("Pöttöm", 1, 1));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Kicsi", 3, 3));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Széles", 10, 1));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Hosszú", 1, 10));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Téglalap", 2, 5));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Nagy", 10, 10));
        meetingRoomsService.newMeetingRoom(new MeetingRoom("Óriás", 100, 100));
    }

    @Test
    void orderByName() {
        assertEquals(
                List.of("Hosszú", "Kicsi", "Nagy", "Óriás", "Pöttöm", "Széles", "Téglalap"),
                meetingRoomsService.orderByName());
    }

    @Test
    void reverseOrder() {
        assertEquals(
                List.of("Téglalap", "Széles", "Pöttöm", "Óriás", "Nagy", "Kicsi", "Hosszú"),
                meetingRoomsService.reverseOrder());
    }

    @Test
    void evenMeetengRooms() {
        assertEquals(
                List.of("Kicsi", "Óriás", "Széles"),
                meetingRoomsService.evenMeetengRooms());
    }

    @Test
    void sizes() {
        List<MeetingRoom> result = meetingRoomsService.sizes();

        assertEquals(7, result.size());
        assertEquals("Óriás", result.get(0).getName());
        assertEquals("Pöttöm", result.get(6).getName());
    }

    @Test
    void findByExactNameNotExists() {
        assertEquals(Optional.empty(), meetingRoomsService.findByExactName("Not exists"));
    }

    @Test
    void findByExactName() {
        Optional<MeetingRoom> result = meetingRoomsService.findByExactName("Téglalap");

        assertEquals("Téglalap", result.get().getName());
        assertEquals(2, result.get().getWidth());
        assertEquals(5, result.get().getLength());
        assertEquals(10, result.get().getSize());
    }

    @Test
    void findByNamePartUpperCase() {
        List<MeetingRoom> result = meetingRoomsService.findByNamePart("T");

        assertEquals(2, result.size());
        assertEquals("Pöttöm", result.get(0).getName());
        assertEquals("Téglalap", result.get(1).getName());
    }

    @Test
    void findByNamePartLowerCase() {
        List<MeetingRoom> result = meetingRoomsService.findByNamePart("t");

        assertEquals(2, result.size());
        assertEquals("Pöttöm", result.get(0).getName());
        assertEquals("Téglalap", result.get(1).getName());
    }

    @Test
    void findByNamePartNotExists() {
        List<MeetingRoom> result = meetingRoomsService.findByNamePart("not Exist");

        assertEquals(0, result.size());
    }

    @Test
    void findBySize() {
        List<MeetingRoom> result = meetingRoomsService.findBySize(10);

        assertEquals(2, result.size());
        assertTrue(List.of("Óriás", "Nagy").contains(result.get(0).getName()));
        assertTrue(List.of("Óriás", "Nagy").contains(result.get(1).getName()));
    }

    @Test
    void findBySizeAllSmaller() {
        List<MeetingRoom> result = meetingRoomsService.findBySize(10000);

        assertEquals(0, result.size());
    }
}