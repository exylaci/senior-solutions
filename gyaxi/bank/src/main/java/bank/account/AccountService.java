package bank.account;

import bank.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import bank.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public AccountDto createAccount(ModifyAndCreateAccountCommand command) {
        Account account = new Account();
        account.setName(command.getName());
        repository.save(account);
        account.setNumber(String.format("%08d", account.getId() % 100000000));
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto getAccount(long id) {
        return modelMapper.map(findAccount(id), AccountDto.class);
    }

    public List<AccountsDto> getAccounts() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<AccountsDto>>() {
        }.getType());
    }

    @Transactional
    public AccountDto updateAccount(long id, ModifyAndCreateAccountCommand command) {
        Account account = findAccount(id);
        if (account.getName().equals("It is a closed account!")) {
            throw new BadRequestException("/api/account", "Modify account", "The account has already been highlighted to delete.");
        }
        account.setName(command.getName());
        return modelMapper.map(account, AccountDto.class);
    }

    @Transactional
    public void deleteAccount(long id) {
        Account account = findAccount(id);
        account.setName("It is a closed account!");
        account.setAmount(BigDecimal.ZERO);
    }

    private Account findAccount(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("/api/accounts", "Find account", "There is no account with this id: " + id));
    }
}