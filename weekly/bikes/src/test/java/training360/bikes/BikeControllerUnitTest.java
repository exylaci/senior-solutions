package training360.bikes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BikeControllerUnitTest {

    @Mock
    BikeService service;

    @InjectMocks
    BikeController controller;

    @Test
    void getUsersTest() {
        when(service.getUsers())
                .thenReturn(List.of("egyik", "Másik"));
        assertThat(controller.getUsers())
                .hasSize(2)
                .contains("egyik", "Másik");
        verify(service).getUsers();
    }
}
