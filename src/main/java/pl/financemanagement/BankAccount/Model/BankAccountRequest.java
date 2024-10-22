package pl.financemanagement.BankAccount.Model;

import jakarta.validation.constraints.NotBlank;
import pl.financemanagement.Expenses.Model.BasicRequest;

import java.math.BigDecimal;

public class BankAccountRequest extends BasicRequest {

    private String externalId;
    @NotBlank
    private String accountName;
    private long userId;
    private BigDecimal accountBalance;

    public BankAccountRequest(boolean isDemo, BigDecimal accountBalance, long userId, String accountName, String externalId) {
        super(isDemo);
        this.accountBalance = accountBalance;
        this.userId = userId;
        this.accountName = accountName;
        this.externalId = externalId;
    }

    public BankAccountRequest(boolean isDemo, String accountName, long userId) {
        super(isDemo);
        this.accountName = accountName;
        this.userId = userId;
    }

    public BankAccountRequest(long userId) {
        this.userId = userId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}
