package training360.bikes;


import java.time.LocalDateTime;

public class Bike {

    private String bikeId;
    private String userId;
    private LocalDateTime time;
    private double distance;

    public Bike(String bikeId, String userId, LocalDateTime time, double distance) {
        this.bikeId = bikeId;
        this.userId = userId;
        this.time = time;
        this.distance = distance;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getDistance() {
        return distance;
    }
}
