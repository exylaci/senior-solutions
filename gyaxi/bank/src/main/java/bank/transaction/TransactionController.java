package bankremake.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
@Tag(name = "Operations on transactions")
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "do transfer", description = "Create a new transaction and store it into the transactions and accounts datatables.")
    public TransactionDto createTransaction(
            @Valid @RequestBody CreateTransactionCommand command) {
        return service.createTransaction(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get transactions", description = "Create a list from the all transactions.")
    public List<TransactionDto> getTransactions() {
        return service.getTransactions();
    }
}