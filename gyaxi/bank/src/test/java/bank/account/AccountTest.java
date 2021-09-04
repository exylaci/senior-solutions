package bank.account;

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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from accounts"})
class AccountTest {
    @Autowired
    TestRestTemplate template;

    Long id;

    @BeforeEach
    void init() {
        id = template.postForObject(
                "/api/accounts",
                new ModifyAndCreateAccountCommand("John Doe"),
                AccountDto.class).getId();
    }

    @Test
    void testCreateAccount() {
        AccountDto accountDto = template.postForObject(
                "/api/accounts",
                new ModifyAndCreateAccountCommand("Jane Doe"),
                AccountDto.class);
        AccountDto result = template.exchange(
                "/api/accounts/" + accountDto.getId(),
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals("Jane Doe", result.getName());
        assertEquals(8, result.getNumber().length());
        assertEquals(result.getId(), Long.parseLong(result.getNumber()));
        assertEquals(0L, result.getAmount().longValueExact());
        assertEquals(LocalDate.now(), result.getOpened());
    }

    @Test
    void testGetAccount() {
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals("John Doe", result.getName());
        assertEquals(8, result.getNumber().length());
        assertEquals(id, Long.parseLong(result.getNumber()));
        assertEquals(0L, result.getAmount().longValueExact());
        assertEquals(LocalDate.now(), result.getOpened());
    }

    @Test
    void testGetAccounts() {
        template.postForObject(
                "/api/accounts",
                new ModifyAndCreateAccountCommand("Jane Doe"),
                AccountDto.class);
        List<AccountsDto> result = template.exchange(
                "/api/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountsDto>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(2)
                .extracting(AccountsDto::getName)
                .containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    void deleteAccount() {
        template.delete("/api/accounts/{id}", id);
        List<AccountsDto> result = template.exchange(
                "/api/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountsDto>>() {
                })
                .getBody();
        assertThat(result).hasSize(1)
                .extracting(AccountsDto::getName)
                .containsExactly("It is a closed account!");
    }

    @Test
    void testModifyAccount() {
        template.exchange(
                "/api/accounts/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(new ModifyAndCreateAccountCommand("Jane Doe")),
                AccountDto.class).getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void testModifyDeletedAccount() {
        template.delete("/api/accounts/{id}", id);
        Problem problem = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(new ModifyAndCreateAccountCommand("Jane Doe")),
                Problem.class).getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals("It is a closed account!", result.getName());
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }
}