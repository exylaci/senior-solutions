package fightergame.fighter;

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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from fighters"})
public class FighterTest {

    @Autowired
    TestRestTemplate template;

    FighterDto fighter1;
    FighterDto fighter2;

    @BeforeEach
    void init() {
        fighter1 = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Alien", 10, 4),
                FighterDto.class);
        fighter2 = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Predator", 18, 7),
                FighterDto.class);
    }

    @Test
    void testCreateNewFighter() {
        FighterDto result = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("New", 10, 4),
                FighterDto.class);
        assertEquals("New", result.getName());
        assertEquals(10, result.getVitality());
        assertEquals(4, result.getDamage());
    }

    @Test
    void testGetFighters() {
        List<FighterDto> result = template.exchange(
                "/api/fighters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FighterDto>>() {
                })
                .getBody();
        assertThat(result)
                .extracting(FighterDto::getName)
                .containsExactlyInAnyOrder("Alien", "Predator");
    }

    @Test
    void createSameFighterTwice() {
        Problem result = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Alien", 10, 4),
                Problem.class);
        List<FighterDto> fighters = template.exchange(
                "/api/fighters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FighterDto>>() {
                })
                .getBody();

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertThat(fighters)
                .hasSize(2)
                .extracting(FighterDto::getName)
                .containsExactlyInAnyOrder("Alien", "Predator");
    }

    @Test
    void deleteFighter() {
        template.delete("/api/fighters/" + fighter1.getId());
        List<FighterDto> fighters = template.exchange(
                "/api/fighters",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FighterDto>>() {
                })
                .getBody();
        assertThat(fighters)
                .extracting(FighterDto::getName)
                .containsExactly("Predator");
    }

    @Test
    void deleteNotExistingFighter() {
        Problem result = template.exchange(
                "/api/fighters/0",
                HttpMethod.DELETE,
                null,
                Problem.class).getBody();
        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals("There is no fighter with this id: 0", result.getDetail());
    }
}