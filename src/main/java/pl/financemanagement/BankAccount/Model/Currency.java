package pl.financemanagement.BankAccount.Model;

import java.util.List;

public enum Currency {

    PLN,
    GBP,
    AUD,
    EUR,
    USD;

    public static List<String> getAllowedCurrency() {
        return List.of("PLN", "GBP", "AUD", "EUR", "USD");
    }

}
