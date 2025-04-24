package pl.financemanagement.BankAccount.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.financemanagement.BankAccount.Model.Currency;
import pl.financemanagement.User.UserModel.UserAccount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bank_account", schema = "app")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;
    @Column(name = "user_id", nullable = false)
    private Long user;
    private Instant createdOn;
    private Instant modifyOn;
    @Version
    private Long accountVersion;
    @Column(name = "account_name", nullable = false, unique = true)
    private String accountName;
    @Column(nullable = false)
    private BigDecimal accountBalance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

}
