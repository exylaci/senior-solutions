package bankremake.account;

import bankremake.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWithTransactionsDto {
    private Long id;
    private String customerName;
    private String accountNumber;
    private BigDecimal balance;
    private boolean deletedAccount;
    private LocalDate openDate;
    private List<Transaction> transactions;
}