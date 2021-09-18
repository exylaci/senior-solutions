package bankremake.account;

import bankremake.exceptions.BadRequestException;
import bankremake.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final ModelMapper modelMapper;

    public List<AccountMinimalDto> getAccounts() {
        return modelMapper.map(repository.listAccounts(), new TypeToken<List<AccountMinimalDto>>() {
        }.getType());
    }

    public AccountWithTransactionsDto getAccount(long id) {
        return modelMapper.map(findAccount(id), AccountWithTransactionsDto.class);
    }

    @Transactional
    public AccountDto createAccount(String name) {
        checkName(name);
        Account account = new Account(name);
        repository.save(account);
        account.setAccountNumber(String.format("%08d", account.getId() % 100000000));
        return modelMapper.map(account, AccountDto.class);
    }

    @Transactional
    public AccountDto updateAccount(long id, String name) {
        checkName(name);
        Account account = findAccount(id);
        if (account.isDeletedAccount()) {
            throw new BadRequestException("/api/accounts", "Customer name", "This account was deleted: " + account.getAccountNumber());
        }
        account.setCustomerName(name);
        return modelMapper.map(account, AccountDto.class);
    }

    @Transactional
    public void deleteAccount(long id) {
        Account account = findAccount(id);
        if (account.isDeletedAccount()) {
            throw new BadRequestException("/api/accounts", "Customer name", "This account has already been deleted: " + account.getAccountNumber());
        }
        account.setDeletedAccount(true);
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("/api/accounts", "Customer name", "Illegal name for customer: " + name);
        }
    }

    private Account findAccount(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("/api/accounts", "Find account", "There is no account with this id: " + id));
    }

    public Account findAccount(String accountNumber) {
        return repository.find(accountNumber);
    }
}