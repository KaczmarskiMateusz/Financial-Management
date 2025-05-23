package pl.financemanagement.Expense.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.financemanagement.Expense.Model.ExpenseCategory;
import pl.financemanagement.Expense.Model.ExpenseType;
import pl.financemanagement.User.UserModel.UserAccount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expense", schema = "app")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "external_id", nullable = false)
    private UUID externalId;
    @Version
    private int version;
    private Instant createdOn;
    private Instant modifyOn;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private String expenseItem;
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
    private BigDecimal expense;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
    private Long bankAccountId;

}
