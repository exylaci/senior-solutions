package bankremake.transaction;

import bankremake.account.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from transactions", "delete from accounts"})
class TransactionControllerRestIT {
    @Autowired
    TestRestTemplate template;

    AccountDto accountJohn;
    AccountDto accountJane;
    AccountDto accountJoe;
    TransactionDto transactionJoeDeposit;

    @BeforeEach
    void init() {
        accountJohn = template.postForObject(
                "/api/accounts?name=John Doe",
                null,
                AccountDto.class);
        accountJane = template.postForObject(
                "/api/accounts?name=Jane Doe",
                null,
                AccountDto.class);
        accountJoe = template.postForObject(
                "/api/accounts?name=Joe Doe",
                null,
                AccountDto.class);
        template.delete("/api/accounts/{id}", accountJane.getId());
        transactionJoeDeposit = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(3),
                        TransactionType.DEPOSIT),
                TransactionDto.class);
    }

    @Test
    void testDeposit() {
        transactionJoeDeposit = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(1),
                        TransactionType.DEPOSIT),
                TransactionDto.class);
        assertEquals(TransactionType.DEPOSIT, transactionJoeDeposit.getTransactionType());
        assertEquals(4L, transactionJoeDeposit.getNewBalance().longValueExact());
    }

    @Test
    void testWithdraw() {
        TransactionDto transactionJoeWithdraw = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(1),
                        TransactionType.WITHDRAW),
                TransactionDto.class);
        assertEquals(TransactionType.WITHDRAW, transactionJoeWithdraw.getTransactionType());
        assertEquals(2L, transactionJoeWithdraw.getNewBalance().longValueExact());
    }

    @Test
    void testWithdrawNotCovered() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(4),
                        TransactionType.WITHDRAW),
                Problem.class);
        List<TransactionDto> result = template.exchange(
                "/api/transactions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TransactionDto>>() {
                })
                .getBody();
//        AccountDto accountDto = template.exchange(
//                "/api/accounts/" + accountJoe.getId(),
//                HttpMethod.GET,
//                null,
//                AccountDto.class)
//                .getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertThat(result).hasSize(1);
//        assertEquals(3L, accountDto.getBalance().longValueExact());
    }

    @Test
    void testDepositNotExists() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        "00000000",
                        null,
                        BigDecimal.valueOf(1),
                        TransactionType.DEPOSIT),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testDepositDeleted() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJane.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(1),
                        TransactionType.DEPOSIT),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testDepositNegativ() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        null,
                        BigDecimal.valueOf(-1),
                        TransactionType.DEPOSIT),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void testSend() {
        TransactionDto transactionJoeToJohn = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        accountJohn.getAccountNumber(),
                        BigDecimal.valueOf(1),
                        TransactionType.SEND),
                TransactionDto.class);
        assertEquals(TransactionType.SEND, transactionJoeToJohn.getTransactionType());
        assertEquals(2L, transactionJoeToJohn.getNewBalance().longValueExact());
        assertEquals(accountJoe.getAccountNumber(), transactionJoeToJohn.getAccountNumberSource());
        assertEquals(accountJohn.getAccountNumber(), transactionJoeToJohn.getAccountNumberTarget());
        assertEquals(LocalDateTime.now().getDayOfMonth(), transactionJoeToJohn.getTransactionTimestamp().getDayOfMonth());
    }

    @Test
    void testSendNotCovered() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJohn.getAccountNumber(),
                        accountJoe.getAccountNumber(),
                        BigDecimal.valueOf(1),
                        TransactionType.SEND),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testReceive() {
        TransactionDto transactionJohnFromJoe = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJohn.getAccountNumber(),
                        accountJoe.getAccountNumber(),
                        BigDecimal.valueOf(1),
                        TransactionType.RECEIVE),
                TransactionDto.class);
        assertEquals(TransactionType.RECEIVE, transactionJohnFromJoe.getTransactionType());
        assertEquals(1L, transactionJohnFromJoe.getNewBalance().longValueExact());
        assertEquals(accountJohn.getAccountNumber(), transactionJohnFromJoe.getAccountNumberSource());
        assertEquals(accountJoe.getAccountNumber(), transactionJohnFromJoe.getAccountNumberTarget());
        assertEquals(LocalDateTime.now().getDayOfMonth(), transactionJohnFromJoe.getTransactionTimestamp().getDayOfMonth());
    }

    @Test
    void testAllTransaction() {
        List<TransactionDto> result = template.exchange(
                "/api/transactions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TransactionDto>>() {
                })
                .getBody();
        assertThat(result)
                .extracting(TransactionDto::getAccountNumberSource)
                .containsExactlyInAnyOrder(transactionJoeDeposit.getAccountNumberSource());
    }

    @Test
    void testDepositInvalidSourceAccountNumber() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        "1234567.",
                        null,
                        BigDecimal.valueOf(1),
                        TransactionType.DEPOSIT),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void testSendInvalidTargetAccountNumber() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        "1234567a",
                        BigDecimal.valueOf(1),
                        TransactionType.SEND),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Disabled
    @Test
    void testSendToThemself() {
        Problem problem = template.postForObject(
                "/api/transactions",
                new CreateTransactionCommand(
                        accountJoe.getAccountNumber(),
                        accountJoe.getAccountNumber(),
                        BigDecimal.valueOf(1),
                        TransactionType.SEND),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}