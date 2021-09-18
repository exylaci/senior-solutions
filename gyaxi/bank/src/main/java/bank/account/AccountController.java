package bankremake.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
@Tag(name = "Operations on accounts")
public class AccountController {
    private final AccountService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get accounts", description = "Create a list from the all account.")
    public List<AccountMinimalDto> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get account", description = "Get all information about an account.")
    public AccountWithTransactionsDto getAccount(
            @PathVariable("id") long id) {
        return service.getAccount(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create account", description = "Create a new account and store it into the accounts datatable.")
    public AccountDto createAccount(
            @JsonProperty String name) {
        return service.createAccount(name);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "modify an account", description = "Modify the details of an existing account.")
    public AccountDto updateAccount(
            @PathVariable("id") long id,
            @JsonProperty String name) {
        return service.updateAccount(id, name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Account not found")
    @Operation(summary = "delete an account", description = "Mark an existing account as deleted.")
    public void deleteAccount(
            @PathVariable("id") long id) {
        service.deleteAccount(id);
    }
}