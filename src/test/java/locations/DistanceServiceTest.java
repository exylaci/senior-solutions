package locations;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DistanceServiceTest {

    @Test
    void calculateDistance() {

        LocationRepository repository = mock(LocationRepository.class);
        DistanceService service = new DistanceService(repository);

        when(repository.findByName(any())).thenReturn(Optional.of(new Location("kamu", 47.1, 19.5)));
        when(repository.findByName(argThat(o -> o.isBlank()))).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), service.calculateDistance("", "másik"));
        assertEquals(Optional.empty(), service.calculateDistance("egyik", ""));
        assertEquals(Optional.of(0.), service.calculateDistance("egyik", "másik"));
    }
}