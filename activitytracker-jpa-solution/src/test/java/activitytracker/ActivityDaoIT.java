package activitytracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.assertj.core.data.Offset;
import org.assertj.core.util.DoubleComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ActivityDaoIT {

    private ActivityDao dao;

    private Activity activity1 = new Activity(LocalDateTime.of(1999, 12, 31, 23, 59), "1. minta adat", Type.BASKETBALL);
    private Activity activity2 = new Activity(LocalDateTime.of(2000, 1, 1, 0, 0), "2. minta adat", Type.HIKING);


    @BeforeEach
    void init() {
        MysqlDataSource source = new MysqlDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/activitytracker");
        source.setUser("activitytracker");
        source.setPassword("activitytracker");

        /*
        Flyway flyway = Flyway.configure().dataSource(source).load();
        flyway.clean();
        flyway.migrate();
        */

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        dao = new ActivityDao(factory);
        dao.save(activity1);
    }

    @Test
    void testSaveAndFind() {
        Long id = activity1.getId();

        Activity result = dao.find(id);

        assertEquals(id, result.getId());
    }

    @Test
    void list() {
        dao.save(activity2);

        List<Activity> result = dao.list();

        assertThat(result).hasSize(2).extracting(Activity::getDesc).containsExactly("1. minta adat", "2. minta adat");
    }

    @Test
    void update() throws InterruptedException {
        Long id = activity1.getId();
        Thread.sleep(1000);

        dao.update(id, "modified description");

        Activity result = dao.find(id);
        assertEquals("modified description", result.getDesc());
        assertNotEquals(result.getCreatedAt(), result.getUpdatedAt());
    }

    @Disabled
    @Test
    void delete() {
        Long id = activity1.getId();
        dao.delete(id);

        List<Activity> result = dao.list();
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdate() throws InterruptedException {
        activity1.setDesc("modified description");

        dao.update(activity1);

        Activity result = dao.find(activity1.getId());
        assertEquals("modified description", result.getDesc());
        assertNotEquals(result.getCreatedAt(), result.getUpdatedAt());
    }

    @Test
    void testSimpleList() {
        activity2.setLabels(List.of("itt", "ott", "valahol"));
        dao.save(activity2);

        Long id = activity2.getId();

        Activity result = dao.findWithList(id);
        assertThat(result.getLabels())
                .hasSize(3)
                .containsExactly("itt", "ott", "valahol");
    }


    @Test
    void testSet() {
        activity2.setPoints(Set.of(
                new TrackPoint(LocalDate.now(), 1.1, 2.2),
                new TrackPoint(LocalDate.now(), 3.1, 4.2),
                new TrackPoint(LocalDate.now(), 5.1, 6.2)));
        dao.save(activity2);

        Long id = activity2.getId();

        Activity result = dao.findWithObjectList(id);
        assertThat(result.getPoints())
                .hasSize(3)
                .extracting(TrackPoint::getLat)
                .usingComparatorForType(new DoubleComparator(0.2), Double.class)
//                .containsSequence(1.1, 3.1, 5.1)
//                .contains(1.1,5.1,3.1)
        ;
    }

    @Test
    void testMap() {
        activity2.setCities(Map.of(
                "Budapest", new City(1L, "Budapest", 2000000),
                "NewYork", new City(2L, "NewYork", 8000000)));
        dao.save(activity2);

        Long id = activity2.getId();

        Activity result = dao.findMap(id);
        assertEquals(2, result.getCities().size());
        assertEquals(2000000, result.getCities().get("Budapest").getPopulation());
    }

    @Test
    void wishAddTest() {
        dao.addWishToBootlist(activity1.getId(), new Wish("mindent és többet"));

        Activity other = dao.findWithBootlist(activity1.getId());

        assertEquals("mindent és többet", other.getBootlist().get(0));
    }

    @Test
    @DisplayName("Secondary tábla teszt.")
    void testDistance() {
        Activity activity = new Activity(LocalDateTime.now(), "Másodlagos tábla vizsgálata", Type.RUNNING, 200.1, 53242L);
        dao.save(activity);

        assertEquals(53242L, dao.find(activity.getId()).getDuration());
        activity.increaseDuration(43L);
        assertEquals(53242L, dao.find(activity.getId()).getDuration());

        dao.update(activity);
        assertEquals(53242L + 43L, dao.find(activity.getId()).getDuration());
    }
}
