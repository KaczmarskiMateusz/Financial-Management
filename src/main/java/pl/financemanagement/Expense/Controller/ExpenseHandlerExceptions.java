package pl.financemanagement.Expense.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.financemanagement.BankAccount.Model.BankAccountErrorResponse;
import pl.financemanagement.Expense.Model.exceptions.ExpenseNotFoundException;
import pl.financemanagement.Expense.Model.exceptions.NotEnoughMoneyForTransaction;

@ControllerAdvice
public class ExpenseHandlerExceptions {

    @ExceptionHandler(NotEnoughMoneyForTransaction.class)
    public ResponseEntity<BankAccountErrorResponse> handleBankAccountNotFoundException() {
        return ResponseEntity.ok().body(
                new BankAccountErrorResponse(false, "There are not enough funds in the account for the account"));
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<BankAccountErrorResponse> handleExpenseNotFoundException() {
        return ResponseEntity.ok().body(
                new BankAccountErrorResponse(false, "Expense doesn't exist"));
    }

}
