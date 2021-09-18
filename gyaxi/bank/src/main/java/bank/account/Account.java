package bankremake.account;

import bankremake.exceptions.BadRequestException;
import bankremake.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "deleted_account")
    private boolean deletedAccount = false;

    @Column(name = "open_date")
    private LocalDate openDate = LocalDate.now();

    @OneToMany(mappedBy = "accountSource", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Transaction> transactions;

    public Account(String customerName) {
        this.customerName = customerName;
    }

    public void increaseBalance(BigDecimal amount) {
        if (amount.signum() < 0 && balance.compareTo(amount.negate()) < 0) {
            throw new BadRequestException("/api/transaction", "create transaction", "There is not cover on the account!");
        }
        balance = balance.add(amount);
    }
}