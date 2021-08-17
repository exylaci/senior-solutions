package org.training360.finalexam.players;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from players"})
public class PlayerControllerRestIT {

    @Autowired
    TestRestTemplate template;


    @Test
    void testAddNewPlayers(){
        PlayerDTO result =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                                PlayerDTO.class);


        assertEquals("John Doe",result.getName());
        assertEquals(1991,result.getDateOfBirth().getYear());
        assertEquals(PositionType.CENTER_BACK,result.getPosition());
    }

    @Test
    void testGetPlayers(){
        template.postForObject("/api/players",
                new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                PlayerDTO.class);

        template.postForObject("/api/players",
                new CreatePlayerCommand("Jack Doe", LocalDate.of(1992,11,10),PositionType.RIGHT_WINGER),
                PlayerDTO.class);

        List<PlayerDTO> result = template.exchange(
                "/api/players",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PlayerDTO>>() {
                }
        ).getBody();


        assertThat(result).extracting(PlayerDTO::getName)
                .containsExactly("John Doe","Jack Doe");
    }

    @Test
    void deletePlayerById(){
        PlayerDTO result =template.postForObject("/api/players",
                new CreatePlayerCommand("John Doe", LocalDate.of(1991,11,10),PositionType.CENTER_BACK),
                PlayerDTO.class);


        template.delete("/api/players/{id}", result.getId());

        List<PlayerDTO> players = template.exchange(
                "/api/players",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PlayerDTO>>() {
                }
        ).getBody();


        assertThat(players).isEmpty();

    }

    @Test
    void testCreatePlayerWithInvalidName(){
        Problem result =
                template.postForObject("/api/players",
                        new CreatePlayerCommand("", LocalDate.now(), PositionType.CENTER_BACK),
                        Problem.class);

        assertEquals(Status.BAD_REQUEST,result.getStatus());
    }




}
