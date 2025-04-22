package pl.financemanagement.Expense.Model.exceptions;

public class NotEnoughMoneyForTransaction extends RuntimeException {

    public NotEnoughMoneyForTransaction(String message) {
        super(message);
    }
}
