package bank.account;

import bank.transaction.TransactionDto;
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
    private String name;
    private String number;
    private BigDecimal amount;
    private LocalDate opened;
    private List<TransactionDto> transactions;
}