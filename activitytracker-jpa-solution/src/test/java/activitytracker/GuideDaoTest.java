package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GuideDaoTest {

    private GuideDao guideDao;
    private ActivityDao activityDao;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        guideDao = new GuideDao(factory);
        activityDao = new ActivityDao(factory);
    }

    @Test
    void saveAndFind() {
        guideDao.save(new Guide("test data"));

        Guide result = guideDao.find("test data");

        assertEquals("test data", result.getName());
    }

    @Test
    void saveActivityWithGuide() {
        Guide guide = new Guide("name of guide");
        guideDao.save(guide);

        Activity activity = new Activity(LocalDateTime.of(1999, 12, 31, 23, 59), "1. minta adat", Type.BASKETBALL);
        activity.setGuide(guide);

        activityDao.save(activity);

        Activity resultActivity = activityDao.list().get(0);
        assertEquals("name of guide", resultActivity.getGuide().getName());

        Guide resultGuide = guideDao.find("name of guide");
        assertEquals("name of guide", resultGuide.getName());
        assertEquals(resultGuide.getId(), resultActivity.getGuide().getId());
    }
}