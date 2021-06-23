package car;

import java.util.List;

public class Car {
    private String brand;
    private String type;
    private int age;
    private Status status;
    private List<KmState> kmStates;

    public Car(String brand, String type, int age, Status status, List<KmState> kmStates) {
        this.brand = brand;
        this.type = type;
        this.age = age;
        this.status = status;
        this.kmStates = kmStates;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public Status getStatus() {
        return status;
    }

    public List<KmState> getKmStates() {
        return kmStates;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setKmStates(List<KmState> kmStates) {
        this.kmStates = kmStates;
    }
}
