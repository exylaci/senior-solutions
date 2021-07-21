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

//        Activity merged = manager.merge(activity);                  //nem javasolt mód, inkább a setterekkel állítsuk
//        merged.setDesc("modified by merge - " + activity.getDesc());
        Activity updated = find(activity.getId());

        updated.setDesc(activity.getDesc());
        updated.setDuration(activity.getDuration());

        manager.getTransaction().commit();
        manager.close();
    }

    public void delete(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.getReference(Activity.class, id);
        //                          //csak egy hivatkozást ad rá, nem tölti be az adtbázisból az értékeket

        manager.getTransaction().begin();
        manager.remove(activity);
        manager.getTransaction().commit();
        manager.close();
    }

    public Activity findWithObjectList(Long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager
                .createQuery("select e from Activity e join fetch e.points where e.id =: id", Activity.class)
                .setParameter("id", id)          // join fetch e.points feltölti a segédtáblából a List<Point> listát is.
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

        // Activity activity = manager.find(Activity.class,activityId);     //felesleges az objektumm teljes feltöltése
        Activity activity = manager.getReference(Activity.class, activityId);       //elegendő csak a referenciáját
        // betölteni, mert csak azt kell beletenni a wish visszamutató attribútumába csak a külső kulcsot kell a wish-ben
        // beállítani, ez így gyorsabb, mert nem tölti be az egész objektumot, csak a referenciáját
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
