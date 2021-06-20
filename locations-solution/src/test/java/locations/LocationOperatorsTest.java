package locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationOperatorsTest {

    Location location1 = new LocationParser().parse("Budapest,47.497912,19.040235");
    Location location2 = new LocationParser().parse("Északon,0.1,-19.040235");
    Location location3 = new LocationParser().parse("Délen,-47.497912,19.040235");

    List<Location> locations = List.of(location1, location2, location3);

    @DisplayName("Északiakra szűrés")
    @Test
    @LocationOperationsFeatureTest
    void filterOnNorth() {
        assertEquals(List.of("Budapest", "Északon"),
                new LocationOperators().filterOnNorth(locations).stream().map(Location::getName).toList());
    }
}