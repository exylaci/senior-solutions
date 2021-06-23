package car;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    @Test
    void getCars() {
        List<Car> result = new CarService().getCars();
        assertEquals(2, result.size());
    }

    @Test
    void getTypes() {
        assertEquals(List.of("Opel", "Trabant"), new CarService().getTypes());

    }
}