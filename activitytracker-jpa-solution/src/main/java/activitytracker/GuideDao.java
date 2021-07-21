package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GuideDao {

    private EntityManagerFactory entityManagerFactory;

    public GuideDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Guide guide) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(guide);
        manager.getTransaction().commit();
        manager.close();
    }

    public Guide find(String name) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        Guide guide = manager
                .createQuery("select e from Guide e where e.name=:name", Guide.class)
                .setParameter("name", name)
                .getSingleResult();
        manager.close();
        return guide;
    }
}