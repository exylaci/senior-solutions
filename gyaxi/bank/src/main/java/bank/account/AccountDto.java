package bankremake.account;

import bankremake.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String customerName;
    private String accountNumber;
    private BigDecimal balance;
    private boolean deletedAccount;
    private LocalDate openDate;
    @JsonBackReference
    private List<Transaction> transactions;
}