package meetingrooms;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMeetingRoomsRepository implements MeetingRoomsRepository {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    @Override
    public void deleteAll() {
        meetingRooms = new ArrayList<>();
    }

    @Override
    public void newMeetingRoom(MeetingRoom meetingRoom) {
        long maxId = meetingRooms
                .stream()
                .map(MeetingRoom::getId)
                .max(Comparator.naturalOrder())
                .orElse(0L);

        meetingRooms.add(new MeetingRoom(maxId + 1, meetingRoom));
    }

    @Override
    public List<String> orderByName() {
        return meetingRooms
                .stream()
                .map(MeetingRoom::getName)
                .sorted(Comparator.nullsLast(Collator.getInstance(new Locale("hu", "HU"))))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> reverseOrder() {
        return meetingRooms
                .stream()
                .map(MeetingRoom::getName)
                .sorted(Comparator.nullsLast(Collator.getInstance(new Locale("hu", "HU"))).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> sizes() {
        return meetingRooms.stream()
                .sorted(Comparator.comparing(MeetingRoom::getSize).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MeetingRoom> findByExactName(String name) {
        return meetingRooms.stream()
                .filter(meetingRoom -> meetingRoom.getName().equals(name))
                .findAny();
    }

    @Override
    public List<MeetingRoom> findByNamePart(String namePart) {
        return meetingRooms.stream()
                .filter(meetingRoom -> meetingRoom.getName().toLowerCase().contains(namePart.toLowerCase()))
                .sorted(Comparator.comparing(MeetingRoom::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> findBySize(int size) {
        return meetingRooms.stream()
                .filter(meetingRoom -> meetingRoom.getSize() > size)
                .collect(Collectors.toList());
    }
}
