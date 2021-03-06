package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ActivityDao {
    private EntityManagerFactory factory;

    public ActivityDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void save(Activity activity) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(activity);
        manager.getTransaction().commit();
        manager.close();
    }

    public Activity find(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.find(Activity.class, id);
        manager.close();
        return activity;
    }

    public Activity findWithList(long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager
                .createQuery("select e from Activity e join fetch e.labels where e.id =: id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        manager.close();
        return activity;
    }

    public List<Activity> list() {
        EntityManager manager = factory.createEntityManager();
        List<Activity> result = manager
                .createQuery("select e from Activity e order by e.desc", Activity.class)
                .getResultList();
        manager.close();
        return result;
    }

    public void update(Long id, String description) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.find(Activity.class, id);

        manager.getTransaction().begin();
        activity.setDesc(description);
        manager.getTransaction().commit();
        manager.close();
    }

    public void update(Activity activity) {
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();

//        Activity merged = manager.merge(activity);                  //nem javasolt m??d, ink??bb a setterekkel ??ll??tsuk
//        merged.setDesc("modified by merge - " + activity.getDesc());

        Activity updated = manager.find(Activity.class, activity.getId());  //helyette find ut??n setterekkel
        updated.setDesc(activity.getDesc());
        updated.setDuration(activity.getDuration());

        manager.getTransaction().commit();
        manager.close();
    }

    public void delete(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.getReference(Activity.class, id);
        //                          //csak egy hivatkoz??st ad r??, nem t??lti be az adtb??zisb??l az ??rt??keket

        manager.getTransaction().begin();
        manager.remove(activity);
        manager.getTransaction().commit();
        manager.close();
    }

    public Activity findWithObjectList(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager
                .createQuery("select e from Activity e join fetch e.points where e.id =: id", Activity.class)
                .setParameter("id", id)          // join fetch e.points felt??lti a seg??dt??bl??b??l a List<Point> list??t is.
                .getSingleResult();
        manager.close();
        return activity;
    }

    public Activity findMap(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager
                .createQuery("select e from Activity e join fetch e.cities where e.id =: id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        manager.close();
        return activity;
    }

    public void addWishToBootlist(Long activityId, Wish wish) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        // Activity activity = manager.find(Activity.class,activityId);     //felesleges az objektumm teljes felt??lt??se
        Activity activity = manager.getReference(Activity.class, activityId);       //elegend?? csak a referenci??j??t
        // bet??lteni, mert csak azt kell beletenni a wish visszamutat?? attrib??tum??ba csak a k??ls?? kulcsot kell a wish-ben
        // be??ll??tani, ez ??gy gyorsabb, mert nem t??lti be az eg??sz objektumot, csak a referenci??j??t
        wish.setActivity(activity);
        manager.persist(wish);

        manager.getTransaction().commit();
        manager.close();

    }

    public Activity findWithBootlist(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager
                .createQuery("select e from Activity e join fetch e.bootlist where e.id =:id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        manager.close();
        return activity;
    }

}
