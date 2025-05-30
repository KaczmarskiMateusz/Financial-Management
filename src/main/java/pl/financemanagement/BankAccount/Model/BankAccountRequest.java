package pl.financemanagement.BankAccount.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountRequest {

    private UUID externalId;
    @NotBlank(message = "account name cannot be empty")
    private String accountName;
    @NotNull(message = "account balance cannot be null")
    @DecimalMin(value = "0.0", message = "account balance should be more then 0")
    private BigDecimal accountBalance;
    @NotNull(message = "Currency cannot be null and must be one of: AUD, PLN, GBP, USD, EUR")
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private Currency currency;

}
