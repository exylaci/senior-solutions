package activitytracker;

import javax.persistence.*;         //JPA kezelje az entitás @annotációit
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity                             //Ahhoz, hogy entitás (ezzekkel dolgozik a JPA) legyen, ne csak egy sima osztály
@Table(name = "activities")         //employees legyen az adattábla neve
@SecondaryTable(name = "activity_details")  //akárhány attribútum egy másik, másodlagos táblában van tárolva (vanbenne egy id erre az osztályra)
@Access(AccessType.FIELD)           //A séma álapértelmezettten az attribútumok alapján készüljön el.
//                                  // (AccessType.PROPERTY) -> getterek és setterek alapján. Nem javasolt.
public class Activity {             //Entitás osztály

    @TableGenerator(name = "activity_gen", table = "act_id_gen", pkColumnName = "id_gen", valueColumnName = "id_val",
            pkColumnValue = "act_gen", initialValue = 10000, allocationSize = 100)
    @GeneratedValue(generator = "actitvity_gen") // a fenti act_gen tábla alapján oszt értéket
//    @GeneratedValue(strategy = GenerationType.TABLE) //általa "kitalált" adatbázis tábla alapján oszt értéket
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //az adatbázis kezelő AUTO_INCREMENT-tel generálja az értékét
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) //az MYSQL sequencia generátora generálja az értékét
//    @GeneratedValue(generator = "activity_gen") //az "activity_gen" tábla alapján ossza a generált értékeket
//    @SequenceGenerator(name="activity_gen", sequenceName = "act_seq") //a generáló tábla létrehozása (mysql nem támogatja)
    //ehhez kell egy adattábla is: CREATE SEQUENCE activity-geg MINVALUE 1 START WITH 1 INCREMENT BY 50;
    @Id                             //Ez lesz a PRIMARY KEY
    private Long id;

    @Column(name = "start_time", nullable = false)  // start_time legyen a mező neve, nem lehet null
    private LocalDateTime startTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "act_description", length = 75)  // act_description legyen a mező neve, VARCHAR(75)
    private String desc;

    @Column(name = "act_type", nullable = false)    // act_type legyen a mező neve, nem lehet null
    @Enumerated(EnumType.STRING)    // az enum neve és nem a sorszáma tárolódik el az adatbázisban
    private Type type;

    @ElementCollection              // Kollekciók eltárolásához ez az @annotáció kell (külön táblába menti)
//    @ElementCollection(FetchType.EAGER) // Mindig betölti a listákat is. Lassúúúú, nem javallott! (N+1 probléma)
    @CollectionTable(name = "labels", joinColumns = @JoinColumn(name = "act_id")) //tábla neve, külső kulcs mező neve
    @Column(name = "label")           // mező név
    private List<String> labels = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "points", joinColumns = @JoinColumn(name = "act_id"))   //tábla neve, külső kulcs mező neve
    @AttributeOverride(name = "time", column = @Column(name = "date"))              // atribútum név -> mező név
    @AttributeOverride(name = "lat", column = @Column(name = "lattitude"))          // atribútum név -> mező név
    @AttributeOverride(name = "lon", column = @Column(name = "longitude"))          // atribútum név -> mező név
    private Set<TrackPoint> points;


    @ElementCollection
    @CollectionTable(name = "cities", joinColumns = @JoinColumn(name = "act_id"))
    //                                  //az adattábla neve, kölső kulcs mező neve
    @MapKeyColumn(name = "city_name")   //kulcsot tartalmazó mező neve
    // @Column(name="érték_mező_neve")  //Amikor csak "egyszerű" burkoló osztály az érték a Map-ben.
    private Map<String, City> cities;

    @OneToOne
    private Guide guide;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "activity", orphanRemoval = true)
    //hozzáadást(létrehozást) és a törlést kaszkádolva
    //mappedBy, hogy ne legyen kapcsolótábla = másik osztályban így hívják az erre az osztályra "mutató" attribútumot
    //az árván maradt child adat törölhető
    @OrderBy("wish")            //wish szerint rendezze
    @OrderColumn(name = "index")
    private List<Wish> bootlist;

    public void addWish(Wish wish) {
        if (bootlist == null) {
            bootlist = new ArrayList<>();
        }
        bootlist.add(wish);
        wish.setActivity(this);  // A másik (gyerek) adatan beállítani a visszamutato attribútumot erre a objektumra
    }

    @Column(table = "activity_details")
    private double distance;
    @Column(table = "activity_details")
    private Long duration;

    public Activity(LocalDateTime startTime, String desc, Type type, double distance, Long duration) {
        this.startTime = startTime;
        this.desc = desc;
        this.type = type;
        this.distance = distance;
        this.duration = duration;
    }

    public Activity() {
    }

    public Activity(LocalDateTime startTime, String desc, Type type) {
        this.startTime = startTime;
        this.desc = desc;
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Set<TrackPoint> getPoints() {
        return points;
    }

    public void setPoints(Set<TrackPoint> points) {
        this.points = points;
    }

    public Map<String, City> getCities() {
        return cities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public List<Wish> getBootlist() {
        return bootlist;
    }

    public void setBootlist(List<Wish> bootlist) {
        this.bootlist = bootlist;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getDuration() {
        return duration;
    }

    public void increaseDuration(Long duration) {
        this.duration += duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }
}
