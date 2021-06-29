package training360.bikes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BikeServiceTest {

    @Test
    void getBikes() {
        List<Bike> result = new BikeService().getBikes();
        assertThat(result)
                .hasSize(5)
                .extracting(Bike::getDistance)
                .contains(0.8, 2.9);
    }

    @Test
    void getUsers() {
        List<String> result = new BikeService().getUsers();
        assertThat(result)
                .hasSize(5)
                .contains("US3434", "US346");
    }

}