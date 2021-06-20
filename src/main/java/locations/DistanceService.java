package locations;

import java.util.Optional;

public class DistanceService {

    LocationRepository repository;

    public DistanceService(LocationRepository repository) {
        this.repository = repository;
    }

    Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> location1 = repository.findByName(name1);
        Optional<Location> location2 = repository.findByName(name2);

        if (location1.isEmpty() || location2.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(location1.get().distanceFrom(location2.get()));
    }
}
