package telephoneregister.numbertype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
@Sql(statements = {"delete from number_types"})
class NumberTypeControllerTest {
    @Autowired
    TestRestTemplate template;

    Long id;

    @BeforeEach
    void init() {
        id = template.postForObject(
                "/api/number-types?numbertype=HOME",
                null,
                NumberTypeDto.class).getId();
    }

    @Test
    void getNumberTypes() {
        template.postForObject(
                "/api/number-types?numbertype=WORK",
                null,
                NumberTypeDto.class).getId();
        List<NumberTypeDto> result = template.exchange(
                "/api/number-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NumberTypeDto>>() {
                })
                .getBody();
        assertThat(result)
                .extracting(NumberTypeDto::getTypeName)
                .containsExactlyInAnyOrder("HOME", "WORK");
    }

    @Test
    void createNumberType() {
        NumberTypeDto result = template.postForObject(
                "/api/number-types?numbertype=WORK",
                null,
                NumberTypeDto.class);
        assertEquals("WORK", result.getTypeName());
    }

    @Test
    void createNumberTypeTwice() {
        template.postForObject(
                "/api/number-types?numbertype=HOME",
                null,
                NumberTypeDto.class);
        List<NumberTypeDto> result = template.exchange(
                "/api/number-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NumberTypeDto>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(1)
                .extracting(NumberTypeDto::getTypeName)
                .containsExactlyInAnyOrder("HOME");
    }

    @Test
    void updateNumberType() {
        template.put(
                "/api/number-types/" + id + "?numbertype=WORK",
                null,
                NumberTypeDto.class);
        List<NumberTypeDto> result = template.exchange(
                "/api/number-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NumberTypeDto>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(1)
                .extracting(NumberTypeDto::getTypeName)
                .containsExactly("WORK");
    }

    @Test
    void deleteNumberType() {
        NumberTypeDto result = template.exchange(
                "/api/number-types/" + id,
                HttpMethod.DELETE,
                null,
                NumberTypeDto.class)
                .getBody();

        List<NumberTypeDto> numberTypes = template.exchange(
                "/api/number-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NumberTypeDto>>() {
                })
                .getBody();

        assertEquals("HOME", result.getTypeName());
        assertThat(numberTypes).isEmpty();
    }

    @Test
    void deleteNotExistingNumberType() {
        Problem problem = template.exchange(
                "/api/number-types/0",
                HttpMethod.DELETE,
                null,
                Problem.class)
                .getBody();

        List<NumberTypeDto> numberTypes = template.exchange(
                "/api/number-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NumberTypeDto>>() {
                })
                .getBody();

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("/api/number-types", problem.getType().getPath());
        assertThat(numberTypes).hasSize(1);
    }
}