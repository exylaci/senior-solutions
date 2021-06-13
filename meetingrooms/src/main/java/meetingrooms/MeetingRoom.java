package meetingrooms;

public class MeetingRoom {
    private final Long id;
    private final String name;
    private final int width;
    private final int length;

    public MeetingRoom(String name, int width, int length) {
        this(null, name, width, length);
    }

    public MeetingRoom(Long id, MeetingRoom meetingRoom) {
        this(id, meetingRoom.getName(), meetingRoom.getWidth(), meetingRoom.getLength());
    }

    public MeetingRoom(Long id, String name, int width, int length) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String reportFormat() {
        return String.format("%20s : %3d x %-3d = %6d", name, width, length, width * length);
    }

    public int getSize() {
        return width * length;
    }
}
