package activitytracker;

import javax.persistence.*;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wish;

    private int index;

    @ManyToOne
    @JoinColumn(name = "activity_id") //másik tábla mezőjére mutató mező neve
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Wish() {
    }

    public Wish(String wish) {
        this.wish = wish;
    }

    public Wish(Long id, String wish) {
        this.id = id;
        this.wish = wish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
