package pl.financemanagement.Expense.Service;

import pl.financemanagement.Expense.Model.ExpenseCategory;
import pl.financemanagement.Expense.Model.ExpenseDto;
import pl.financemanagement.Expense.Model.ExpenseRequest;
import pl.financemanagement.Expense.Model.ExpenseResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponse createExpense(ExpenseRequest expenseRequest, String email);

    ExpenseResponse updateExpense(ExpenseRequest expenseRequest, String email);

    List<ExpenseDto> findExpensesForUserByBankAccount(String email, UUID bankAccountExternalId);

    ExpenseResponse findExpenseByIdAndUserId(String email, UUID expenseExternalId);

    void deleteExpenseByUserExternalIdAndExpenseExternalId(String email, UUID expenseExternalId);

    List<ExpenseCategory> getExpensesCategories();

}
