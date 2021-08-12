package org.training360.finalexam.teams;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.PlayerDTO;
import org.training360.finalexam.players.PositionType;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from players","delete from teams"})
public class TeamControllerRestIT {

    @Autowired
    TestRestTemplate template;


    @Test
    void testCreateNewTeam(){
        TeamDTO result =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Arsenal"),
                        TeamDTO.class);

        assertEquals("Arsenal",result.getName());

    }


    @Test
    void testGetTeams(){
        template.postForObject("/api/teams",
                new CreateTeamCommand("Arsenal"),
                TeamDTO.class);

        template.postForObject("/api/teams",
                new CreateTeamCommand("Chelsea"),
                TeamDTO.class);

        List<TeamDTO> result = template.exchange(
                "/api/teams",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeamDTO>>() {
                }
        ).getBody();

        assertThat(result).extracting(TeamDTO::getName)
                .containsExactly("Arsenal","Chelsea");
    }


    @Test
    void testAddNewPlayerToExistingTeam(){
        TeamDTO team =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Arsenal"),
                        TeamDTO.class);

        TeamDTO resultWithPlayer = template.postForObject("/api/teams/{id}/players",
                new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10), PositionType.CENTER_BACK),
                TeamDTO.class,
                team.getId());

        assertThat(resultWithPlayer.getPlayers()).extracting(PlayerDTO::getName)
                .containsExactly("John Doe");

    }

    @Test
    void testAddExistingPlayerToExistingTeam(){
        TeamDTO team =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Arsenal"),
                        TeamDTO.class);

        PlayerDTO player =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                        PlayerDTO.class);

        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player.getId()), team.getId());


        List<TeamDTO> result = template.exchange(
                "/api/teams",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeamDTO>>() {
                }
        ).getBody();

        assertThat(result.get(0).getPlayers()).extracting(PlayerDTO::getName)
                .containsExactly("John Doe");
    }


    @Test
    void testAddExistingPlayerWithTeam(){
        TeamDTO team1 =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Arsenal"),
                        TeamDTO.class);

        TeamDTO team2 =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Chelsea"),
                        TeamDTO.class);

        PlayerDTO player =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                        PlayerDTO.class);

        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player.getId()), team1.getId());

        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player.getId()), team2.getId());

        List<TeamDTO> result = template.exchange(
                "/api/teams",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeamDTO>>() {
                }
        ).getBody();

        TeamDTO resultTeam1 = result.stream().filter(t->t.getName().equals("Arsenal")).findFirst().orElseThrow();
        TeamDTO resultTeam2 = result.stream().filter(t->t.getName().equals("Chelsea")).findFirst().orElseThrow();

        assertThat(resultTeam1.getPlayers()).extracting(PlayerDTO::getName)
                .containsExactly("John Doe");

        assertThat(resultTeam2.getPlayers()).isEmpty();

    }

    @Test
    void testAddPlayerWithPosition(){
        TeamDTO team1 =
                template.postForObject("/api/teams",
                        new CreateTeamCommand("Arsenal"),
                        TeamDTO.class);

        PlayerDTO player =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                        PlayerDTO.class);

        PlayerDTO player2 =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("Jack Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                        PlayerDTO.class);

        PlayerDTO player3 =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("Jill Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                        PlayerDTO.class);

        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player.getId()), team1.getId());
        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player2.getId()), team1.getId());
        template.put("/api/teams/{id}/players", new UpdateWithExistingPlayerCommand(player3.getId()), team1.getId());

        List<TeamDTO> result = template.exchange(
                "/api/teams",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeamDTO>>() {
                }
        ).getBody();

        assertThat(result.get(0).getPlayers()).extracting(PlayerDTO::getName)
                .containsOnly("John Doe","Jack Doe");

    }

    @Test
    void testAddPlayerToNotExistingTeam(){
        Long wrongId = 6666L;

       Problem result = template.postForObject("/api/teams/{id}/players",
                new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                Problem.class,
                wrongId);

       assertEquals(URI.create("teams/not-found"),result.getType());
       assertEquals(Status.NOT_FOUND,result.getStatus());
    }

    @Test
    void testCreateTeamWithInvalidName(){
        Problem result =
                template.postForObject("/api/teams",
                        new CreateTeamCommand(""),
                        Problem.class);

        assertEquals(Status.BAD_REQUEST,result.getStatus());
    }
}
