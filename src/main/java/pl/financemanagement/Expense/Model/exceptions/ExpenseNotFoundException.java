package pl.financemanagement.Expense.Model.exceptions;

public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

}
