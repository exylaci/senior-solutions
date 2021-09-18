package bankremake.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.deletedAccount=FALSE ORDER BY a.customerName")
    List<Account> listAccounts();

    @Query("SELECT a FROM Account a WHERE a.accountNumber=:accountNumber AND a.deletedAccount=FALSE")
    Account find(String accountNumber);
}