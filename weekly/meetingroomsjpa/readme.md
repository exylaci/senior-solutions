JPA-val implementáld a következő interfészt!

Használjatok Lombokot! Írjatok integrációs teszteket!

```java
public interface MeetingRoomsRepository {

  MeetingRoom save(String name, int width, int length);

  List<String> getMeetingroomsOrderedByName();

  List<String> getEverySecondMeetingRoom();

  List<MeetingRoom> getMeetingRooms();

  List<MeetingRoom> getExactMeetingRoomByName(String name);

  List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix);

  void deleteAll();
}
```

Ahol az entitás:

```java
public class MeetingRoom {

    private long id;
    private String name;
    private int width;
    private int length;

}
```