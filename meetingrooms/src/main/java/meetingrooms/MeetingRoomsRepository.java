package meetingrooms;

import java.util.List;
import java.util.Optional;

public interface MeetingRoomsRepository {


    void newMeetingRoom(MeetingRoom meetingRoom);

    List<String> orderByName();

    List<String> reverseOrder();

    List<MeetingRoom> sizes();

    Optional<MeetingRoom> findByExactName(String name);

    List<MeetingRoom> findByNamePart(String namePart);

    List<MeetingRoom> findBySize(int size);

    void deleteAll();
}