package pl.financemanagement.Expenses.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ExpenseRequest {

    private UUID externalId;
    @NotNull(message = "expense category cannot be blank")
    private ExpenseCategory expenseCategory;
    @NotNull(message = "expense type cannot be blank")
    private ExpenseType expenseType;
    @NotNull(message = "bankBalance cannot be null")
    private BigDecimal bankBalance;
    private BigDecimal expenseCost;
    private UUID bankAccountExternalId;

}
