package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationsControllerTestIT {

    @Autowired
    LocationsController locationsController;

    @Test
    void getLocations() {
        assertThat(locationsController.getLocations())
                .hasSize(6)
                .contains("Lent messze délen","Északi-sark");
    }
}