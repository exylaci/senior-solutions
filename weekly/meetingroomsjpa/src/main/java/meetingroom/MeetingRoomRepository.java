package meetingroom;

import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public class MeetingRoomRepository {
    private EntityManagerFactory factory;

    public MeetingRoom save(String name, int width, int length) {
        MeetingRoom meetingRoom = new MeetingRoom(name, width, length);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(meetingRoom);
        manager.getTransaction().commit();
        manager.close();

        return meetingRoom;
    }

    List<String> getMeetingRoomsOrderedByName() {
        EntityManager manager = factory.createEntityManager();

        // List<String> result = manager.createNamedQuery("SelectAllByName", String.class).getResultList();
        List<String> result = manager.createQuery(
                "SELECT m.name FROM MeetingRoom m ORDER BY m.name",
                String.class)
                .getResultList();
        manager.close();

        return result;
    }

    List<String> getEverySecondMeetingRoom() {
        List<String> names = getMeetingRoomsOrderedByName();
        List<String> result = Stream.iterate(1, n -> n + 2)
                .limit(names.size() / 2)
                .map(n -> names.get(n))
                .toList();
        return result;
    }

    List<MeetingRoom> getMeetingRooms() {
        EntityManager manager = factory.createEntityManager();
        List<MeetingRoom> result = manager.createQuery(
                "SELECT m FROM MeetingRoom m ORDER BY m.name",
                MeetingRoom.class)
                .getResultList();
        manager.close();

        return result;

    }

    Optional<MeetingRoom> getExactMeetingRoomByName(String name) {
        EntityManager manager = factory.createEntityManager();
        Optional<MeetingRoom> result = manager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.name =:name",
                MeetingRoom.class)
                .setParameter("name", name)
                .getResultStream()
                .findAny();
        manager.close();

        return result;
    }

    List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        EntityManager manager = factory.createEntityManager();
        List<MeetingRoom> result = manager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.name LIKE :name",
                MeetingRoom.class)
                .setParameter("name", nameOrPrefix + "%")
                .getResultList();
        manager.close();

        return result;
    }

    void deleteAll(){
        EntityManager manager=factory.createEntityManager();
        manager.getTransaction().begin();
        manager.createQuery("DELETE FROM MeetingRoom m").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }
}
