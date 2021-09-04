package bank.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
@Tag(name = "Operations on account")
public class AccountController {
    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create account", description = "Create a new account and store it into the accounts datatable.")
    public AccountDto createAccount(
            @Valid @RequestBody ModifyAndCreateAccountCommand command) {
        return service.createAccount(command);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "load account", description = "Load all information of an account.")
    public AccountDto getAccount(
            @PathVariable("id") long id) {
        return service.getAccount(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get accounts", description = "Create a list from the all accounts.")
    public List<AccountsDto> getPlayers() {
        return service.getAccounts();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Account not found")
    @Operation(summary = "modify an account", description = "Modify the details of an existing account, if it is not deleted.")
    public AccountDto updateAccount(
            @PathVariable("id") long id,
            @RequestBody ModifyAndCreateAccountCommand command) {
        return service.updateAccount(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Account not found")
    @Operation(summary = "delete an account", description = "Highlight as deleted an existing account in the accounts datatable.")
    public void deleteAccount(
            @PathVariable("id") long id) {
        service.deleteAccount(id);
    }
}