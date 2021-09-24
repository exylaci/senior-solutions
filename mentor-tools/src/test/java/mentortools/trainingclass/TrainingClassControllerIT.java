package mentortools.trainingclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from training_classes")
class TrainingClassControllerIT {
    @Autowired
    TestRestTemplate template;

    Long id;

    @BeforeEach
    void init() {
        TrainingClassDto trainingClassDto = template.postForObject(
                "/api/trainingclasses",
                new CreateUpdateTrainingClassCommand(
                        "Java backend junior",
                        new BeginEndDates(
                                LocalDate.of(2020, 10, 25),
                                LocalDate.of(2021, 3, 31))),
                TrainingClassDto.class);
        id = trainingClassDto.getId();

        template.postForObject(
                "/api/trainingclasses",
                new CreateUpdateTrainingClassCommand(
                        "Java backend senior",
                        new BeginEndDates(
                                LocalDate.of(2021, 6, 1),
                                LocalDate.of(2021, 6, 2))),
                TrainingClassDto.class);
        template.postForObject(
                "/api/trainingclasses",
                new CreateUpdateTrainingClassCommand(
                        "Java frontend",
                        new BeginEndDates(
                                LocalDate.of(2020, 10, 25),
                                LocalDate.of(2021, 3, 31))),
                TrainingClassDto.class);
    }

    @Test
    void testGetTrainingClasses() {
        List<TrainingClassDto> expected = template.exchange(
                "/api/trainingclasses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }).getBody();

        assertThat(expected)
                .extracting(TrainingClassDto::getTitle)
                .containsExactly("Java backend junior", "Java backend senior", "Java frontend");
    }

    @Test
    void testGetTrainingClass() {
        TrainingClassDto expected = template.exchange(
                "/api/trainingclasses/" + id,
                HttpMethod.GET,
                null,
                TrainingClassDto.class)
                .getBody();

        assertEquals("Java backend junior", expected.getTitle());
        assertEquals(LocalDate.of(2020, 10, 25), expected.getBegin());
        assertEquals(LocalDate.of(2021, 3, 31), expected.getEnd());
    }


    @Test
    void testUpdateTrainingClasses() {
        template.put(
                "/api/trainingclasses/" + id,
                new CreateUpdateTrainingClassCommand(
                        "Java backend modified",
                        new BeginEndDates(
                                LocalDate.of(2022, 1, 1),
                                LocalDate.of(2022, 1, 2))),
                TrainingClassDto.class);

        TrainingClassDto expected = template.exchange(
                "/api/trainingclasses/" + id,
                HttpMethod.GET,
                null,
                TrainingClassDto.class).getBody();

        assertEquals("Java backend modified", expected.getTitle());
        assertEquals(LocalDate.of(2022, 1, 1), expected.getBegin());
        assertEquals(LocalDate.of(2022, 1, 2), expected.getEnd());
    }


    @Test
    void testDeleteTrainingClasses() {
        template.delete("/api/trainingclasses/" + id);

        List<TrainingClassDto> expected = template.exchange(
                "/api/trainingclasses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }).getBody();

        assertThat(expected)
                .extracting(TrainingClassDto::getId)
                .doesNotContain(id);
    }
}