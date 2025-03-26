package pl.financemanagement.BankAccount.Model;

import org.springframework.stereotype.Component;
import pl.financemanagement.BankAccount.Model.Entity.BankAccount;

@Component
public class BankAccountMapper {

    public BankAccountDto mapToAccountDto(BankAccount bankAccount) {
        return new BankAccountDto.BankAccountDtoBuilder()
                .externalId(bankAccount.getExternalId())
                .accountName(bankAccount.getAccountName())
                .accountBalance(bankAccount.getAccountBalance())
                .currency(bankAccount.getCurrency())
                .build();
    }


}
