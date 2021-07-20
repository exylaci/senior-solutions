package meetingroom;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.array;
import static org.junit.jupiter.api.Assertions.*;

class MeetingRoomRepositoryIT {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    MeetingRoomRepository repository = new MeetingRoomRepository(factory);

    @BeforeEach
    void init() {
        repository.save("Pöttöm", 1, 1);
        repository.save("Kicsi", 3, 3);
        repository.save("Széles", 10, 1);
        repository.save("Hosszú", 1, 10);
        repository.save("Téglalap", 2, 5);
        repository.save("Nagy", 10, 10);
        repository.save("Óriás", 100, 100);
    }

    @Test
    @DisplayName("List the names of all the meeting rooms by name")
    void testListByName() {

        List<String> result = repository.getMeetingRoomsOrderedByName();

        assertEquals(List.of("Hosszú", "Kicsi", "Nagy", "Óriás", "Pöttöm", "Széles", "Téglalap"), result);
    }

    @Test
    @DisplayName("Save new MeetingRooms and check by List all.")
    void testSaveAndListByName() {
        repository.save("még egy tárgyaló", 10, 20);

        List<String> result = repository.getMeetingRoomsOrderedByName();

        assertEquals(List.of("Hosszú", "Kicsi", "még egy tárgyaló", "Nagy", "Óriás", "Pöttöm", "Széles", "Téglalap"), result);
    }

    @Test
    @DisplayName("List the names of every second meeting room.")
    void evenMeetengRooms() {
        assertEquals(List.of("Kicsi", "Óriás", "Széles"), repository.getEverySecondMeetingRoom());
    }

    @Test
    @DisplayName("List every meeting room.")
    void getMeetingRooms() {
        List<MeetingRoom> result = repository.getMeetingRooms();

        assertThat(result).extracting(MeetingRoom::getName)
                .containsExactly("Hosszú", "Kicsi", "Nagy", "Óriás", "Pöttöm", "Széles", "Téglalap");
    }

    @Test
    @DisplayName("Find a meeting room by its exact name, Giving existing name.")
    void testGetExactMeetingRoomByName() {
        assertEquals("Óriás", repository.getExactMeetingRoomByName("Óriás").get().getName());
    }

    @Test
    @DisplayName("Find a meeting room by its exact name. Giving not existing name.")
    void testGetExactMeetingRoomByNameDoesntExists() {
        assertEquals(Optional.empty(), repository.getExactMeetingRoomByName("Doesn't exists this name."));
    }

    @Test
    @DisplayName("Find a meeting rooms by part of the beginning of their names.")
    void testGetMeetingRoomsByPrefix() {
        List<MeetingRoom> result = repository.getMeetingRoomsByPrefix("Ó");

        assertThat(result).extracting(MeetingRoom::getName).containsExactly("Óriás");
    }

    @Test
    @DisplayName("Delete all the meeting rooms.")
    void testDeleteAll() {
        repository.deleteAll();
        assertEquals(0, repository.getMeetingRooms().size());
    }
}