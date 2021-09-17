package training360.guinnessapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import training360.guinnessapp.dto.RecorderCreateCommand;
import training360.guinnessapp.dto.RecorderDto;
import training360.guinnessapp.dto.RecorderShortDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from world_record", "delete from recorder"})
public class RecordersListIT {

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("Ben", LocalDate.of(1998, 9, 9));
        template.postForObject("/api/recorders", inputCommand, RecorderDto.class);

        inputCommand = new RecorderCreateCommand("Jack", LocalDate.of(1999, 9, 9));
        template.postForObject("/api/recorders", inputCommand, RecorderDto.class);

        inputCommand = new RecorderCreateCommand("Glen", LocalDate.of(1999, 9, 9));
        template.postForObject("/api/recorders", inputCommand, RecorderDto.class);
    }

    @Test
    void test_listSuccessful() {
        List<RecorderShortDto> recorders =
                template.exchange("/api/recorders", HttpMethod.GET, null, new ParameterizedTypeReference<List<RecorderShortDto>>() {
                }).getBody();
        assertThat(recorders)
                .extracting(RecorderShortDto::getName)
                .containsExactly("Glen", "Ben");
    }
}