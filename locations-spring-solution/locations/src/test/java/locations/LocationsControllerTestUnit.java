package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTestUnit {

    private List<Location> locations = List.of(
            new Location(4L, "itt is", -31., -61.),
            new Location(4L, "ott is", 0., 90.)
    );


    @Mock
    LocationService locationService;

    @InjectMocks
    LocationsController locationsController;

    @Test
    void getLocations() {
        when(locationService.getLocations())
                .thenReturn(locations);

        assertThat(locationsController.getLocations())
                .hasSize(2)
                .contains("itt is","ott is");

        verify(locationService,times(1)).getLocations();
    }

}