package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();           // tranzakció elindítása
        Activity activity1 = new Activity(LocalDateTime.of(1999, 12, 31, 23, 59), "1. minta adat", Type.BASKETBALL);
        Activity activity2 = new Activity(LocalDateTime.of(2000, 1, 1, 0, 0), "2. minta adat", Type.HIKING);
        manager.persist(activity1);                 // adat felvétele
        manager.persist(activity2);                 // adat felvétele
        manager.getTransaction().commit();          // tranzakció lezárása Csak ekkor kerül bele az adatbázisba!

        Activity result = manager.find(Activity.class, activity1.getId());
        //                                          // adat keresése kinyerése ID alapján
        //                                          // (Ehhez nem kell tranzakciót indítani.)

        manager.getTransaction().begin();           // tranzakció elindítása
        result.setDesc("megváltoztatott adat");     // elegendő csak az entitás setterét hívva átírni az adatot
        manager.getTransaction().commit();          // módosított adat eltárolása adatbázisban

        List<Activity> activities =                 // adatok lekérdezése listába
                manager
                        .createQuery("select e from Activity e order by e.desc", Activity.class)
                        .getResultList();           // e = Entitás Osztályt reprezentálja
        //                                          // Activity az entitás osztály neve
        //                                          // e.attribútum = rendezés alapja
        // System.out.println(activities);

        manager.getTransaction().begin();
        manager.remove(result);                     // adat törlésre jejölése
        manager.getTransaction().commit();          // törlés végrehajtása

        manager.close();                            // lezárások, különben a futás nem áll le!
        factory.close();
    }

}
