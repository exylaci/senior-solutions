package car;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private List<Car> cars = List.of(
            new Car("Opel", "Volta", 1, Status.KIVÁLLÓ, List.of(
                    new KmState(LocalDate.of(2020, 12, 25), 0),
                    new KmState(LocalDate.of(2021, 5, 12), 1375))),
            new Car("Trabant", "Limusin", 51, Status.ROSSZ, List.of(
                    new KmState(LocalDate.of(1971, 5, 17), 0),
                    new KmState(LocalDate.of(2000, 1, 1), 800000),
                    new KmState(LocalDate.of(2021, 6, 1), 840375))));

    public List<Car> getCars() {
        return cars;
    }

    public List<String> getTypes() {
        return cars
                .stream()
                .map(Car::getBrand)
                .distinct()
                .toList();
    }
}
