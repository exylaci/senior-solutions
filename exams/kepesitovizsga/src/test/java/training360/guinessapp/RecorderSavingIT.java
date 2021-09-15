package training360.guinessapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import training360.guinessapp.dto.RecorderCreateCommand;
import training360.guinessapp.dto.RecorderDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from world_record", "delete from recorder"})
public class RecorderSavingIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void test_saveSuccessful() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("Ben", LocalDate.of(1999, 9, 9));
        RecorderDto recorder = template.postForObject("/api/recorders", inputCommand, RecorderDto.class);
        assertEquals("Ben", recorder.getName());
        assertEquals(LocalDate.of(1999, 9, 9), recorder.getDateOfBirth());
    }

    @Test
    void test_NameBlank() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("", LocalDate.of(1999, 9, 9));
        Problem problem = template.postForObject("/api/recorders", inputCommand, Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("must not be blank", (((List<Map<String, String>>) problem.getParameters().get("violations")).get(0)).get("message"));
    }


    @Test
    void test_dateOfBirthInFuture() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("", LocalDate.of(2999, 9, 9));
        Problem problem = template.postForObject("/api/recorders", inputCommand, Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("must be in the past", (((List<Map<String, String>>) problem.getParameters().get("violations")).get(0)).get("message"));
    }
}
