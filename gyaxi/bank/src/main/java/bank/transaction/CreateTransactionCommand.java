package bankremake.transaction;

import bankremake.validations.CheckAccountNumberFormat;
import bankremake.validations.CheckIfItIsNeeded;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionCommand {
    @CheckAccountNumberFormat(exactlength = 8, message = "Account number must be exactly 8 digits!")
    private String accountNumberSource;

    @CheckIfItIsNeeded(message = "Account number must be exactly 8 digits and different from the source one!")
    private String accountNumberTarget;

    @Positive
    private BigDecimal amount;

    @NotNull
    private TransactionType transactionType;
}