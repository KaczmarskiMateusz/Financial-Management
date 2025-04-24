package pl.financemanagement.BankAccount.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.financemanagement.BankAccount.Model.Entity.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
              FROM app.bank_account
             WHERE user_id     = :userId
               AND external_id = :externalId"""
    )
    Optional<BankAccount> findByUserAndExternalId(@Param("userId") Long userId,
                                                  @Param("externalId") UUID externalId);

    @Query(nativeQuery = true, value = """
            select currency
            from app.bank_account 
            where user_id = :user_id """)
    List<String> findAllAccountCurrenciesForUser(@Param("user_id") long userId);

    @Query(nativeQuery = true, value = """
            select *
            from app.bank_account
            where user_id = :user_id""")
    List<BankAccount> findBankAccountByUserId(@Param("user_id") long userId);

}
