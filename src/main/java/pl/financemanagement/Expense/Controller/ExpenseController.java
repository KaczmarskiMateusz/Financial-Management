package pl.financemanagement.Expense.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.financemanagement.ApplicationConfig.DemoResolver.DemoResolver;
import pl.financemanagement.Expense.Model.ExpenseCategory;
import pl.financemanagement.Expense.Model.ExpenseDto;
import pl.financemanagement.Expense.Model.ExpenseRequest;
import pl.financemanagement.Expense.Model.ExpenseResponse;
import pl.financemanagement.Expense.Service.ExpenseService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/expense")
public class ExpenseController extends DemoResolver<ExpenseService> {

    @Autowired
    public ExpenseController(@Qualifier("expenseServiceImpl") ExpenseService service,
                             @Qualifier("expenseServiceDemo") ExpenseService demoService) {
        super(service, demoService);
    }

    @PostMapping()
    ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid ExpenseRequest request,
                                                  BindingResult result,
                                                  Principal principal) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(buildErrorResponse(result));
        }
        return ResponseEntity.ok(resolveService(principal.getName()).createExpense(request, principal.getName()));
    }

    @PutMapping()
    ResponseEntity<ExpenseResponse> updateExpense(@RequestBody @Valid ExpenseRequest request,
                                                  BindingResult result,
                                                  Principal principal) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(buildErrorResponse(result));
        }
        return ResponseEntity.ok(resolveService(principal.getName()).updateExpense(request, principal.getName()));
    }

    @GetMapping("/{bankAccountExternalId}/list")
    ResponseEntity<List<ExpenseDto>> findExpenses(@PathVariable UUID bankAccountExternalId,
                                                  Principal principal) {
        return ResponseEntity.ok(resolveService(
                principal.getName()).findExpensesForUserByBankAccount(principal.getName(), bankAccountExternalId));
    }

    @GetMapping("/{externalId}")
    ResponseEntity<ExpenseResponse> findExpense(@PathVariable @Valid UUID externalId,
                                                 Principal principal) {
        return ResponseEntity.ok(resolveService(
                principal.getName()).findExpenseByIdAndUserId(principal.getName(), externalId));
    }

    @GetMapping("/categories")
    public List<ExpenseCategory> getExpensesCategories() {
        return service().getExpensesCategories();
    }

    private ExpenseResponse buildErrorResponse(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ExpenseResponse(false, errors);
    }

}
