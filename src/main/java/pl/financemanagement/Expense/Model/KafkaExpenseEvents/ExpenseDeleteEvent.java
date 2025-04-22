package pl.financemanagement.Expense.Model.KafkaExpenseEvents;


import pl.financemanagement.Expense.Model.Entity.Expense;

public class ExpenseDeleteEvent {

    private Expense expense;

    public ExpenseDeleteEvent(Expense expense) {
        this.expense = expense;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}

