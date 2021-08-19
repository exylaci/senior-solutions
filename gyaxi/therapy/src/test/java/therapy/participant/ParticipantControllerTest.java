package therapy.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from sessions", "delete from participants"})
class ParticipantControllerTest {
    @Autowired
    TestRestTemplate template;

    Long id;

    @BeforeEach
    void init() {
        template.postForObject(
                "/api/participants?name=1st Participant",
                null,
                ParticipantDto.class);
        id = template.postForObject(
                "/api/participants?name=2nd Participant",
                null,
                ParticipantDto.class).getId();
    }

    @Test
    void createParticipant() {
        ParticipantDto result = template.postForObject(
                "/api/participants?name=Name of Participant",
                null,
                ParticipantDto.class);
        assertEquals("Name of Participant", result.getName());
        assertEquals(null, result.getSession());
    }

    @Test
    void createParticipantWithInvalidNameEmpty() {
        Problem result = template.postForObject(
                "/api/participants?name=",
                null,
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createParticipantWithInvalidNameDifferent() {
        Problem result = template.postForObject(
                "/api/participants?neve=Modified",
                null,
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void getParticipants() {
        List<ParticipantDto> result = template.exchange(
                "/api/participants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ParticipantDto>>() {
                }).getBody();
        assertThat(result)
                .extracting(ParticipantDto::getName)
                .containsExactlyInAnyOrder("1st Participant", "2nd Participant");
    }

    @Test
    void getParticipant() {
        ParticipantDto result = template.exchange(
                "/api/participants/" + id,
                HttpMethod.GET,
                null,
                ParticipantDto.class).getBody();
        assertEquals("2nd Participant", result.getName());
    }

    @Test
    void updateParticipant() {
        template.put(
                "/api/participants/" + id,
                new UpdateParticipantCommand("Modified Name"));
        ParticipantDto result = template.exchange(
                "/api/participants/" + id,
                HttpMethod.GET,
                null,
                ParticipantDto.class).getBody();
        assertEquals("Modified Name", result.getName());
    }

    @Test
    void updateParticipantWithInvalidNameNull() {
        template.put(
                "/api/participants/" + id,
                new UpdateParticipantCommand(null));
        ParticipantDto result = template.exchange(
                "/api/participants/" + id,
                HttpMethod.GET,
                null,
                ParticipantDto.class).getBody();
        assertEquals("2nd Participant", result.getName());
    }

    @Test
    void updateParticipantWithInvalidNameEmpty() {
        template.put(
                "/api/participants/" + id,
                new UpdateParticipantCommand(""));
        ParticipantDto result = template.exchange(
                "/api/participants/" + id,
                HttpMethod.GET,
                null,
                ParticipantDto.class).getBody();
        assertEquals("2nd Participant", result.getName());
    }

    @Test
    void deleteParticipant() {
        template.delete("/api/participants/" + id);
        List<ParticipantDto> result = template.exchange(
                "/api/participants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ParticipantDto>>() {
                }).getBody();
        assertThat(result)
                .extracting(ParticipantDto::getName)
                .containsExactly("1st Participant");
    }
}