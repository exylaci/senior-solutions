package bankremake.transaction;

import bankremake.account.Account;
import bankremake.account.AccountService;
import bankremake.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService service;
    private final ModelMapper modelMapper;


    public TransactionDto createTransaction(CreateTransactionCommand command) {
        TransactionDto transactionDto = null;

        switch (command.getTransactionType()) {
            case DEPOSIT -> transactionDto = deposit(command);
            case WITHDRAW -> transactionDto = withdraw(command);
            case SEND -> transactionDto = send(command);
            case RECEIVE -> transactionDto = receive(command);
        }

        return transactionDto;
    }

    @Transactional
    public TransactionDto deposit(CreateTransactionCommand command) {
        Account accountSource = findAccount(command.getAccountNumberSource());
        accountSource.increaseBalance(command.getAmount());

        Transaction transaction = new Transaction(accountSource, command.getAmount(), accountSource.getBalance(), command.getTransactionType());
        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Transactional
    public TransactionDto withdraw(CreateTransactionCommand command) {
        Account accountSource = findAccount(command.getAccountNumberSource());
        accountSource.increaseBalance(command.getAmount().negate());

        Transaction transaction = new Transaction(accountSource, command.getAmount(), accountSource.getBalance(), command.getTransactionType());
        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Transactional
    public TransactionDto send(CreateTransactionCommand command) {
        Account accountSource = findAccount(command.getAccountNumberSource());
        accountSource.increaseBalance(command.getAmount().negate());

        Account accountTarget = findAccount(command.getAccountNumberTarget());
        accountTarget.increaseBalance(command.getAmount());

        Transaction transaction = new Transaction(accountSource, command.getAmount(), accountSource.getBalance(), command.getTransactionType());
        transaction.setAccountNumberTarget(accountTarget.getAccountNumber());
        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Transactional
    public TransactionDto receive(CreateTransactionCommand command) {
        Account accountTarget = findAccount(command.getAccountNumberTarget());
        accountTarget.increaseBalance(command.getAmount().negate());

        Account accountSource = findAccount(command.getAccountNumberSource());
        accountSource.increaseBalance(command.getAmount());

        Transaction transaction = new Transaction(accountSource, command.getAmount(), accountSource.getBalance(), command.getTransactionType());
        transaction.setAccountNumberTarget(accountTarget.getAccountNumber());
        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    private Account findAccount(String accountNumber) {
        Account account = service.findAccount(accountNumber);
        if (account == null) {
            throw new BadRequestException("/api/transactions", "Create transaction", "There is no bank account with this number: " + accountNumber);
        }
        return account;
    }

    public List<TransactionDto> getTransactions() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<TransactionDto>>() {
        }.getType());
    }
}