package activitytracker;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable             // tudjon belőle adattáblát csinálni a JPA
public class TrackPoint {
    private LocalDate time;
    private double lat;
    private double lon;

    public TrackPoint() {
    }

    public TrackPoint(LocalDate time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
