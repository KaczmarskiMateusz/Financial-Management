package pl.financemanagement.BankAccount.Model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
public class BankAccountDto {

    private final UUID externalId;
    private final String accountName;
    private final UUID accountNumber;
    private final BigDecimal accountBalance;
    private final Currency currency;

}
