package bankremake.account;

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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from transactions", "delete from accounts"})
class AccountControllerRestIT {
    @Autowired
    TestRestTemplate template;

    long id;

    @BeforeEach
    void init() {
        id = template.postForObject(
                "/api/accounts?name=John Doe",
                null,
                AccountDto.class).getId();
    }

    @Test
    void testCreateAccount() {
        AccountDto accountDto = template.postForObject(
                "/api/accounts?name=Jane Doe",
                null,
                AccountDto.class);
        AccountWithTransactionsDto result = template.exchange(
                "/api/accounts/" + accountDto.getId(),
                HttpMethod.GET,
                null,
                AccountWithTransactionsDto.class)
                .getBody();
        assertEquals("Jane Doe", result.getCustomerName());
        assertEquals(8, result.getAccountNumber().length());
        assertEquals(result.getId(), Long.parseLong(result.getAccountNumber()));
        assertEquals(0L, result.getBalance().longValueExact());
        assertEquals(LocalDate.now(), result.getOpenDate());
        assertTrue(result.getTransactions().isEmpty());
    }

    @Test
    void testCreateAccountInvalidNameEmpty() {
        Problem problem = template.postForObject(
                "/api/accounts?name= ",
                null,
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testCreateAccountInvalidNameNull() {
        Problem problem = template.postForObject(
                "/api/accounts?other=Jane Doe",
                null,
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testModifyAccount() {
        AccountDto accountDto = template.exchange(
                "/api/accounts/" + id + "?name=Jane Doe",
                HttpMethod.PUT,
                null,
                AccountDto.class)
                .getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals("Jane Doe", result.getCustomerName());
    }

    @Test
    void testModifyAccountInvalidName() {
        Problem problem = template.exchange(
                "/api/accounts/" + id + "?name=",
                HttpMethod.PUT,
                null,
                Problem.class)
                .getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("John Doe", result.getCustomerName());
    }

    @Test
    void testModifyAccountInvalidId() {
        Problem problem = template.exchange(
                "/api/accounts/0?name=Jane Doe",
                HttpMethod.PUT,
                null,
                Problem.class)
                .getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("John Doe", result.getCustomerName());
    }

    @Test
    void testModifyDeletedAccount() {
        template.delete("/api/accounts/{id}", id);
        Problem problem = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.DELETE,
                null,
                Problem.class).getBody();
        AccountDto result = template.exchange(
                "/api/accounts/" + id,
                HttpMethod.GET,
                null,
                AccountDto.class)
                .getBody();
        assertTrue(result.isDeletedAccount());
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testGetAccounts() {
        template.postForObject(
                "/api/accounts?name=Jane Doe",
                null,
                AccountDto.class);
        List<AccountMinimalDto> result = template.exchange(
                "/api/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountMinimalDto>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(2)
                .extracting(AccountMinimalDto::getCustomerName)
                .containsExactly("Jane Doe", "John Doe");
    }

    @Test
    void testGetAccountsDeleted() {
        template.postForObject(
                "/api/accounts?name=Jane Doe",
                null,
                AccountDto.class);
        template.delete("/api/accounts/{id}", id);
        List<AccountMinimalDto> result = template.exchange(
                "/api/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountMinimalDto>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(1)
                .extracting(AccountMinimalDto::getCustomerName)
                .containsExactly("Jane Doe");
    }
}