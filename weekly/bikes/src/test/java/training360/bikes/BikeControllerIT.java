package training360.bikes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BikeControllerIT {

    @Autowired
    BikeController controller;

    @Test
    void getUserTest() {
        assertThat(controller.getUsers())
                .hasSize(5)
                .contains("US346");
    }
}
