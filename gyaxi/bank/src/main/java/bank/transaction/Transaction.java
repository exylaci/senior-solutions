package bankremake.transaction;

import bankremake.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String accountNumberSource;

    @Column(name = "account_number_target")
    private String accountNumberTarget;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_timestamp")
    private final LocalDateTime transactionTimestamp = LocalDateTime.now();

    @Column(name = "new_balamce")
    private BigDecimal newBalance;

    @ManyToOne
    @JoinColumn(name = "account_source_id")
    @EqualsAndHashCode.Exclude
    private Account accountSource;

    public Transaction(Account accountSource, BigDecimal amount, BigDecimal newBalance, TransactionType transactionType) {
        this.accountSource = accountSource;
        this.amount = amount;
        this.newBalance = newBalance;
        this.transactionType = transactionType;
    }

    @PostLoad
    @PostUpdate
    @PostPersist
    public void setSourceAccountNumber() {
        if (accountSource != null) {
            accountNumberSource = accountSource.getAccountNumber();
        }
    }
}