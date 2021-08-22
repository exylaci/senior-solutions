package therapy.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import therapy.participant.ParticipantDto;
import therapy.participant.ParticipantWithSessionDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from sessions_participants", "delete from participants", "delete from sessions"})
class SessionControllerTest {
    @Autowired
    TestRestTemplate template;

    ParticipantDto participant1;
    ParticipantDto participant2;
    SessionDto sessionDto1;
    SessionDto sessionDto2;

    @BeforeEach
    void init() {
        participant1 = template.postForObject(
                "/api/participants?name=1st Participant",
                null,
                ParticipantDto.class);
        participant2 = template.postForObject(
                "/api/participants?name=2nd Participant",
                null,
                ParticipantDto.class);
        sessionDto1 = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("1st Therapist", "1st location", LocalDateTime.now().plusYears(1)),
                SessionDto.class);
        sessionDto2 = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("2nd therapist", "2nd Location", LocalDateTime.now().plusYears(2)),
                SessionDto.class);
    }

    @Test
    void createSession() {
        SessionDto result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("Name of the therapist", "spotted location", LocalDateTime.now().plusYears(3)),
                SessionDto.class);
        assertEquals("Name of the therapist", result.getTherapist());
        assertEquals("spotted location", result.getLocation());
        assertEquals(LocalDateTime.now().plusYears(3).getYear(), result.getStartsAt().getYear());
        assertNull(result.getParticipants());
    }

    @Test
    void createSessionWithInvalidNameNull() {
        Problem result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand(null, "spotted location", LocalDateTime.now().plusYears(3)),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createSessionWithInvalidNameShort() {
        Problem result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("12 4", "spotted location", LocalDateTime.now().plusYears(3)),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createSessionWithInvalidNameOneWord() {
        Problem result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("OneWord", "spotted location", LocalDateTime.now().plusYears(3)),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createSessionWithInvalidTimePast() {
        Problem result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("Name of the therapist", "spotted location", LocalDateTime.now().minusSeconds(1)),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createSessionWithInvalidTimePresent() {
        Problem result = template.postForObject(
                "/api/sessions",
                new CreateUpdateSessionCommand("Name of the therapist", "spotted location", LocalDateTime.now()),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void getSessions() {
        List<SessionDto> result = template.exchange(
                "/api/sessions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SessionDto>>() {
                }).getBody();
        assertThat(result)
                .extracting(SessionDto::getTherapist)
                .containsExactlyInAnyOrder("1st Therapist", "2nd therapist");
    }

    @Test
    void getSession() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals("2nd therapist", result.getTherapist());
        assertEquals("1st Participant", result.getParticipants().get(0).getName());
    }

    @Test
    void getSessionNotExists() {
        Problem result = template.exchange(
                "/api/sessions/0",
                HttpMethod.GET,
                null,
                Problem.class).getBody();
        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals("There is no session with this id: 0", result.getDetail());
    }

    @Test
    void updateSession() {
        template.put(
                "/api/sessions/" + sessionDto2.getId(),
                new CreateUpdateSessionCommand("Modified name", "Modified location", LocalDateTime.now().plusYears(4)));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals("Modified name", result.getTherapist());
        assertEquals("Modified location", result.getLocation());
        assertEquals(LocalDateTime.now().plusYears(4).getYear(), result.getStartsAt().getYear());
    }

    @Test
    void updateSessionWithInvalidDate() {
        template.put(
                "/api/sessions/" + sessionDto2.getId(),
                new CreateUpdateSessionCommand("Modified name", "Modified location", LocalDateTime.now().minusSeconds(1)));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals("2nd therapist", result.getTherapist());
        assertEquals("2nd Location", result.getLocation());
    }

    @Test
    void updateSessionCheckParticipants() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.put(
                "/api/sessions/" + sessionDto2.getId(),
                new CreateUpdateSessionCommand("Modified name", "Modified location", LocalDateTime.now().minusSeconds(1)));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals("2nd therapist", result.getTherapist());
        assertEquals("2nd Location", result.getLocation());
        assertEquals("2nd Location", result.getLocation());
        assertThat(result.getParticipants())
                .extracting(ParticipantDto::getName)
                .containsExactly("1st Participant");
    }

    @Test
    void deleteSessionWithEmptyList() {
        template.delete("/api/sessions/" + sessionDto2.getId());
        List<SessionDto> result = template.exchange(
                "/api/sessions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SessionDto>>() {
                }).getBody();
        assertThat(result)
                .extracting(SessionDto::getTherapist)
                .containsExactly("1st Therapist");
    }

    @Test
    void deleteSessionWithRegisteredParticipants() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.delete("/api/sessions/" + sessionDto2.getId());
        List<SessionDto> resultSession = template.exchange(
                "/api/sessions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SessionDto>>() {
                }).getBody();
        assertThat(resultSession)
                .extracting(SessionDto::getTherapist)
                .containsExactly("1st Therapist");

        ParticipantDto resultParticipant = template.exchange(
                "/api/participants/" + participant1.getId(),
                HttpMethod.GET,
                null,
                ParticipantDto.class).getBody();
        assertEquals("1st Participant", resultParticipant.getName());
    }

    @Test
    void addParticipant() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertThat(result.getParticipants())
                .extracting(ParticipantDto::getName)
                .containsExactly("1st Participant");
    }

    @Test
    void addParticipantSameTwice() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertThat(result.getParticipants())
                .hasSize(1)
                .extracting(ParticipantDto::getName)
                .containsExactly("1st Participant");
    }

    @Test
    void addParticipantNotExisting() {
        Problem result = template.exchange(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                HttpMethod.PUT,
                new HttpEntity<>(new AddParticipantCommand(0L)),
                Problem.class)
                .getBody();
        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals("There is no participant with this id: 0", result.getDetail());
    }

    @Test
    void removeParticipant() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.delete("/api/sessions/" + sessionDto2.getId() + "/participants/" + participant1.getId());
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals(List.of(), result.getParticipants());
    }

    @Test
    void deleteParticipant() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.delete("/api/participants/" + participant1.getId());
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals(List.of(), result.getParticipants());
    }

    @Test
    void deleteParticipantNotExisting() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        Problem result = template.exchange(
                "/api/sessions/" + sessionDto2.getId() + "/participants/0",
                HttpMethod.DELETE,
                null,
                Problem.class)
                .getBody();
        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals("There is no participant with this id: 0", result.getDetail());
    }

    @Test
    void removeParticipantInvalidParticipant() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.delete("/api/sessions/" + sessionDto2.getId() + "/participants/0");
        SessionDto result = template.exchange(
                "/api/sessions/" + sessionDto2.getId(),
                HttpMethod.GET,
                null,
                SessionDto.class).getBody();
        assertEquals(1, result.getParticipants().size());
    }

    @Test
    void getParticipantWithSession() {
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        ParticipantWithSessionDto result = template.exchange(
                "/api/participants/" + participant1.getId(),
                HttpMethod.GET,
                null,
                ParticipantWithSessionDto.class).getBody();
        assertEquals(participant1.getId(), result.getId());
        assertEquals(sessionDto2.getId(), result.getSessions().get(0).getId());

        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
    }

    @Test
    void getParticipantWithTwoSessions() {
        template.put(
                "/api/sessions/" + sessionDto1.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.put(
                "/api/sessions/" + sessionDto2.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        template.put(
                "/api/sessions/" + sessionDto1.getId() + "/participants",
                new AddParticipantCommand(participant1.getId()));
        ParticipantWithSessionDto result = template.exchange(
                "/api/participants/" + participant1.getId(),
                HttpMethod.GET,
                null,
                ParticipantWithSessionDto.class).getBody();
        assertEquals(2, result.getSessions().size());
    }
}