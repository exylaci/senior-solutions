package fightergame.contest;

import fightergame.fighter.CreateUpdateFighterCommand;
import fightergame.fighter.FighterDto;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from contests", "delete from fighters"})
public class ContestTest {

    @Autowired
    TestRestTemplate template;

    long fighterIdAlien;
    long fighterIdPredator;
    long fighterIdRipley;
//    long contestId;

    @BeforeEach
    void init() {
        fighterIdAlien = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Alien", 11, 7),
                FighterDto.class).getId();
        fighterIdPredator = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Predator", 22, 5),
                FighterDto.class).getId();
        fighterIdRipley = template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Ripley third officer", 8, 15),
                FighterDto.class).getId();
    }

    @Test
    void testDawnResult() {
        ContestDto result = template.postForObject(
                "/api/contests/" + fighterIdRipley + "/" + fighterIdAlien,
                null,
                ContestDto.class);
        assertEquals("Ripley third officer", result.getFighterNameA());
        assertEquals("Alien", result.getFighterNameB());
        assertEquals(Result.WIN_A, result.getResult());
    }

    @Test
    void testStartNewContest() {
        ContestDto result = template.postForObject(
                "/api/contests/" + fighterIdRipley + "/" + fighterIdAlien,
                null,
                ContestDto.class);
        FighterDto fighterA = template.getForObject(
                "/api/fighters/" + fighterIdRipley,
                FighterDto.class);
        FighterDto fighterB = template.getForObject(
                "/api/fighters/" + fighterIdAlien,
                FighterDto.class);

        assertEquals("Ripley third officer", result.getFighterNameA());
        assertEquals("Alien", result.getFighterNameB());
        assertEquals(Result.WIN_A, result.getResult());

        assertEquals(1, fighterA.getScore().getWin());
        assertEquals(0, fighterA.getScore().getLose());

        assertEquals(0, fighterB.getScore().getWin());
        assertEquals(1, fighterB.getScore().getLose());
    }

    @Test
    void testStartNewContestHitBack() {
        ContestDto result = template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdRipley,
                null,
                ContestDto.class);
        FighterDto fighterA = template.getForObject(
                "/api/fighters/" + fighterIdRipley,
                FighterDto.class);
        FighterDto fighterB = template.getForObject(
                "/api/fighters/" + fighterIdAlien,
                FighterDto.class);

        assertEquals("Alien", result.getFighterNameA());
        assertEquals("Ripley third officer", result.getFighterNameB());
        assertEquals(Result.WIN_B, result.getResult());

        assertEquals(1, fighterA.getScore().getWin());
        assertEquals(0, fighterA.getScore().getLose());

        assertEquals(0, fighterB.getScore().getWin());
        assertEquals(1, fighterB.getScore().getLose());
    }

    @Test
    void testStartNewContestWithThemself() {
        Problem result = template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdAlien,
                null,
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("/api/contests", result.getType().getPath());
        assertEquals("Duel", result.getTitle());
        assertEquals("Cannot fight with themself!", result.getDetail());
    }

    @Test
    void startContestSameFightersTwice() {
        template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdPredator,
                null,
                ContestDto.class);
        template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdPredator,
                null,
                ContestDto.class);
        List<ContestDto> result = template.exchange(
                "/api/contests",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContestDto>>() {
                })
                .getBody();
        assertThat(result).hasSize(2);
    }

    @Test
    void testGetContests() {
        template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdPredator,
                null,
                ContestDto.class);
        template.postForObject(
                "/api/contests/" + fighterIdPredator + "/" + fighterIdRipley,
                null,
                ContestDto.class);
        List<ContestDto> result = template.exchange(
                "/api/contests",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContestDto>>() {
                })
                .getBody();
        assertThat(result)
                .extracting(ContestDto::getResult)
                .containsExactlyInAnyOrder(Result.WIN_B, Result.DRAW);
    }

    @Test
    void testGetContest() {
        long id = template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdPredator,
                null,
                ContestDto.class).getId();
        ContestDto result = template.exchange(
                "/api/contests/" + id,
                HttpMethod.GET,
                null,
                ContestDto.class)
                .getBody();
        assertEquals("Alien", result.getFighterNameA());
        assertEquals("Predator", result.getFighterNameB());
        assertEquals(Result.WIN_B, result.getResult());
    }

    @Test
    void testFinalResult() {
         template.postForObject(
                "/api/fighters",
                new CreateUpdateFighterCommand("Terminator", 1000, 1),
                FighterDto.class);
        template.postForObject(
                "/api/contests/" + fighterIdAlien + "/" + fighterIdPredator,
                null,
                ContestDto.class);
        template.postForObject(
                "/api/contests/" + fighterIdPredator + "/" + fighterIdRipley,
                null,
                ContestDto.class);

        List<FighterDto> result = template.exchange(
                "/api/contests/tournament_podium",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FighterDto>>() {
                })
                .getBody();

        assertThat(result).hasSize(3);
        assertEquals("Predator", result.get(0).getName());
        assertEquals("Ripley third officer", result.get(1).getName());
    }
}