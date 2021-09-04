package bank.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import bank.transaction.Transaction;

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
    private String name;
    @Column(name = "account_number")
    private String number;

    private BigDecimal amount = BigDecimal.ZERO;

    private LocalDate opened = LocalDate.now();

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Transaction> transactions;
}