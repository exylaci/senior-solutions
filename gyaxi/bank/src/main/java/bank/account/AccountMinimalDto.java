package bankremake.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMinimalDto {
    private String customerName;
    private String accountNumber;
    private BigDecimal balance;
}