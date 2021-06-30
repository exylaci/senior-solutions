package locations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    @Test
    void getLocations() {
        assertThat(new LocationService().getLocations())
                .hasSize(6)
                .extracting(Location::getName)
                .contains("Lent messze délen","Északi-sark");
    }
}