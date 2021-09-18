package bankremake.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String accountNumberSource;
    private String accountNumberTarget;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime transactionTimestamp;
    private BigDecimal newBalance;
}