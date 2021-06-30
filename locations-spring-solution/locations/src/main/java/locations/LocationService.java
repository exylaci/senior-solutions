package locations;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private List<Location> locations = List.of(
            new Location(1L, "Budapest", 19., 47.),
            new Location(2L, "OrigoBudapest", 0., 0.),
            new Location(3L, "Grinich", 0., 53.),
            new Location(4L, "Lent messze délen", -31., -61.),
            new Location(4L, "Északi-sark", 0., 90.),
            new Location(4L, "Déli-sark", 0., -90.)
    );

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }
}
