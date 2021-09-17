package training360.guinnessapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import training360.guinnessapp.dto.*;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from world_record", "delete from recorder"})
public class WorldRecordBeatIT {

    @Autowired
    TestRestTemplate template;

    long benId;

    long glenId;

    long worldRecordId;

    @BeforeEach
    private void saveTestRecorder() {
        RecorderCreateCommand inputCommand = new RecorderCreateCommand("Ben", LocalDate.of(1999, 9, 9));
        RecorderDto recorder = template.postForObject("/api/recorders", inputCommand, RecorderDto.class);
        benId = recorder.getId();

        inputCommand = new RecorderCreateCommand("Glen", LocalDate.of(1999, 9, 9));
        recorder = template.postForObject("/api/recorders", inputCommand, RecorderDto.class);
        glenId = recorder.getId();

        WorldRecordCreateCommand worldRecordCreateCommand =
                new WorldRecordCreateCommand("Largest pizza", 5.78, "meter", LocalDate.of(2001, 11, 11), benId);
        WorldRecordDto worldRecord = template.postForObject("/api/worldrecords", worldRecordCreateCommand, WorldRecordDto.class);
        worldRecordId = worldRecord.getId();
    }

    @Test
    void test_beatSuccessful() {
        BeatWorldRecordCommand inputCommand = new BeatWorldRecordCommand(glenId, 5.92);
        BeatWorldRecordDto result = template.exchange("/api/worldrecords/{id}/beatrecords",
                HttpMethod.PUT,
                new HttpEntity(inputCommand),
                BeatWorldRecordDto.class, worldRecordId).getBody();


        assertEquals("Largest pizza", result.getDescription());
        assertEquals("meter", result.getUnitOfMeasure());
        assertEquals("Ben", result.getOldRecorderName());
        assertEquals("Glen", result.getNewRecorderName());
        assertEquals(5.92, result.getNewRecordValue(), 0.005);
        assertEquals(0.14, result.getRecordDifference(), 0.005);
    }



    @Test
    void test_RecorderNotExists() {
        BeatWorldRecordCommand inputCommand = new BeatWorldRecordCommand(glenId + 1, 5.92);
        Problem problem = template.exchange("/api/worldrecords/{id}/beatrecords",
                HttpMethod.PUT,
                new HttpEntity(inputCommand),
                Problem.class, worldRecordId).getBody();

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Recorder not found", problem.getTitle());
    }

    @Test
    void test_WorldRecordNotExists() {
        BeatWorldRecordCommand inputCommand = new BeatWorldRecordCommand(glenId, 5.92);
        Problem problem = template.exchange("/api/worldrecords/{id}/beatrecords",
                HttpMethod.PUT,
                new HttpEntity(inputCommand),
                Problem.class, worldRecordId + 1).getBody();

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("World record not found", problem.getTitle());
    }

    @Test
    void test_WorldRecordCanNotBeat() {
        BeatWorldRecordCommand inputCommand = new BeatWorldRecordCommand(glenId, 1.92);
        Problem problem = template.exchange("/api/worldrecords/{id}/beatrecords",
                HttpMethod.PUT,
                new HttpEntity(inputCommand),
                Problem.class, worldRecordId).getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Can not beat", problem.getTitle());
    }
}