package car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {


    @Mock
    CarService carService;

    @InjectMocks
    CarController controller;



    @Test
    void carsList() {
        final List<Car> cars = List.of(
                new Car("Opel", "Volta", 1, Status.KIVÁLLÓ, List.of(
                        new KmState(LocalDate.of(2020, 12, 25), 0),
                        new KmState(LocalDate.of(2021, 5, 12), 1375))),
                new Car("Trabant", "Limusin", 51, Status.ROSSZ, List.of(
                        new KmState(LocalDate.of(1971, 5, 17), 0),
                        new KmState(LocalDate.of(2000, 1, 1), 800000),
                        new KmState(LocalDate.of(2021, 6, 1), 840375))));

        when(carService.getCars()).thenReturn(cars);

        assertThat(controller.carList())
                .hasSize(2)
                .extracting(Car::getBrand)
                .contains("Opel", "Trabant");

        verify(carService, times(1)).getCars();
    }

    @Test
    void typeList() {
        final List<String> requested = List.of("Egyik", "Másik");

        when(carService.getTypes()).thenReturn(requested);

        assertEquals(requested, controller.typeList());
        assertThat(controller.typeList())
                .hasSize(2)
                .contains("Egyik", "Másik");

        verify(carService, times(2)).getTypes();
    }
}