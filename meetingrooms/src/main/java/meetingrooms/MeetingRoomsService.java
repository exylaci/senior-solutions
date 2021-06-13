package meetingrooms;

import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeetingRoomsService {
    private MeetingRoomsRepository meetingRoomsRepository;

    public MeetingRoomsService(MeetingRoomsRepository meetingRoomsRepository) {
        this.meetingRoomsRepository = meetingRoomsRepository;
    }

    public void newMeetingRoom(MeetingRoom meetingRoom) {
        meetingRoomsRepository.newMeetingRoom(meetingRoom);
    }

    public List<String> orderByName() {
        return meetingRoomsRepository.orderByName();
    }

    public List<String> reverseOrder() {
        return meetingRoomsRepository.reverseOrder();
    }

    public List<String> evenMeetengRooms() {
        List<String> ordered = meetingRoomsRepository.orderByName();

        return Stream
                .iterate(1, n -> n + 2)
                .limit(ordered.size() / 2)
                .map(index -> ordered.get(index))
                .collect(Collectors.toList());
    }

    public List<MeetingRoom> sizes() {
        return meetingRoomsRepository.sizes();
    }

    public Optional<MeetingRoom> findByExactName(String name) {
        return meetingRoomsRepository.findByExactName(name);
    }

    public List<MeetingRoom> findByNamePart(String namePart) {
        return meetingRoomsRepository.findByNamePart(namePart);
    }

    public List<MeetingRoom> findBySize(int size) {
        return meetingRoomsRepository.findBySize(size);
    }
}
