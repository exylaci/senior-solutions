package training360.guinessapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import training360.guinessapp.dto.RecorderCreateCommand;
import training360.guinessapp.dto.RecorderDto;
import training360.guinessapp.dto.WorldRecordCreateCommand;
import training360.guinessapp.dto.WorldRecordDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from world_record", "delete from recorder"})
public class WorldRecordSavingIT {

    @Autowired
    TestRestTemplate template;

    long recorderId;

    @BeforeEach
    void saveTestRecorder() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("Ben", LocalDate.of(1999, 9, 9));
        RecorderDto recorder = template.postForObject("/api/recorders", inputCommand, RecorderDto.class);
        recorderId = recorder.getId();
    }

    @Test
    void test_saveSuccessful() {
        WorldRecordCreateCommand inputCommand =
                new WorldRecordCreateCommand("Largest pizza", 5.78, "meter", LocalDate.of(2001, 11, 11), recorderId);

        WorldRecordDto record = template.postForObject("/api/worldrecords", inputCommand, WorldRecordDto.class);

        assertEquals("Largest pizza", record.getDescription());
        assertEquals(5.78, record.getValue(), 0.005);
        assertEquals("meter", record.getUnitOfMeasure());
        assertEquals(LocalDate.of(2001, 11, 11), record.getDateOfRecord());
        assertEquals("Ben", record.getRecorderName());
    }

    @Test
    void test_DescriptionBlank() {
        WorldRecordCreateCommand inputCommand =
                new WorldRecordCreateCommand("", 5.78, "meter", LocalDate.of(2001, 11, 11), recorderId);

        Problem problem = template.postForObject("/api/worldrecords", inputCommand, Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("must not be blank", (((List<Map<String, String>>) problem.getParameters().get("violations")).get(0)).get("message"));
    }

    @Test
    void test_InvalidRecorder() {
        WorldRecordCreateCommand inputCommand =
                new WorldRecordCreateCommand("Largest pizza", 5.78, "meter", LocalDate.of(2001, 11, 11), recorderId + 1);

        Problem problem = template.postForObject("/api/worldrecords", inputCommand, Problem.class);
        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Recorder not found", problem.getTitle());
    }
}