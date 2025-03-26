package pl.financemanagement.BankAccount.Service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.financemanagement.BankAccount.Model.BankAccountDto;
import pl.financemanagement.BankAccount.Model.BankAccountRequest;
import pl.financemanagement.BankAccount.Model.BankAccountResponse;
import pl.financemanagement.BankAccount.Model.Currency;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@Qualifier("bankAccountServiceDemo")
public class BankAccountServiceDemo implements BankAccountService {

    private static final UUID ACCOUNT_NUMBER = UUID.fromString("9e1f957c-3239-43a4-bd36-550e2ebc03ac");

    @Override
    public BankAccountResponse createAccount(BankAccountRequest bankAccountRequest, String email) {
        return new BankAccountResponse(true, buildDemoAccount());
    }

    @Override
    public BankAccountResponse updateAccount(BankAccountRequest bankAccountRequest, String email) {
        return new BankAccountResponse(true, buildDemoAccount());
    }

    @Override
    public BankAccountResponse findAccountByPrincipal(String email, UUID bankAccountExternalId) {
        return new BankAccountResponse(true, buildDemoAccount());
    }

    @Override
    public BankAccountResponse deleteAccount(String email, UUID bankAccountExternalId) {
        return new BankAccountResponse(true);

    }

    @Override
    public BigDecimal getBankAccountBalance(String email, UUID bankAccountExternalId) {
        return BigDecimal.valueOf(10_000);
    }

    @Override
    public List<BankAccountDto> getAllAccounts(String email) {
        return List.of();
    }

    private BankAccountDto buildDemoAccount() {
        return  BankAccountDto.builder()
                .externalId(UUID.randomUUID())
                .accountName("Test account")
                .accountNumber(ACCOUNT_NUMBER)
                .currency(Currency.EUR)
                .accountBalance(BigDecimal.valueOf(10_000_000))
                .build();
    }
}
