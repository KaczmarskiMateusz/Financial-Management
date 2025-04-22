package pl.financemanagement.Expense.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import pl.financemanagement.Expense.Model.ExpenseCategory;
import pl.financemanagement.Expense.Model.ExpenseRequest;
import pl.financemanagement.Expense.Model.ExpenseType;
import pl.financemanagement.Expense.Service.ExpenseService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean(name = "expenseServiceImpl")
    private ExpenseService expenseService;

    @MockBean(name = "expenseServiceDemo")
    private ExpenseService expenseServiceDemo;

    @Test
    void createExpense() throws Exception {
        ExpenseRequest expenseRequest = buildExpenseRequest();

        String request = objectMapper.writeValueAsString(expenseRequest);

        mockMvc.perform(post("/expense")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        verify(expenseService).createExpense(any(), any());
    }

    @Test
    void createExpenseWithWrongRequest() throws Exception {
        ExpenseRequest expenseRequest = buildExpenseRequest();
        expenseRequest.setBankBalance(null);

        String request = objectMapper.writeValueAsString(expenseRequest);

        mockMvc.perform(post("/expense")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateExpense() throws Exception {
        ExpenseRequest expenseRequest = buildExpenseRequest();

        String request = objectMapper.writeValueAsString(expenseRequest);

        mockMvc.perform(put("/expense")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        verify(expenseService).updateExpense(any(), any());
    }

    @Test
    void updateExpenseWithWrongRequest() throws Exception {
        ExpenseRequest expenseRequest = buildExpenseRequest();
        expenseRequest.setExpenseType(null);

        String request = objectMapper.writeValueAsString(expenseRequest);

        mockMvc.perform(put("/expense")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findExpenses() throws Exception {
        mockMvc.perform(get("/expense//{bankAccountExternalId}/list", UUID.randomUUID())
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());

        verify(expenseService).findExpensesForUserByBankAccount(any(), any());
    }

    @Test
    void testFindExpense() throws Exception {
        mockMvc.perform(get("/expense/{externalId}", UUID.randomUUID())
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void getExpensesCategories() throws Exception {
        mockMvc.perform(get("/expense/categories")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());
    }

    private ExpenseRequest buildExpenseRequest() {
        return ExpenseRequest.builder()
                .externalId(UUID.randomUUID())
                .expenseCategory(ExpenseCategory.HOME)
                .expenseCost(BigDecimal.valueOf(100))
                .bankBalance(BigDecimal.valueOf(10_000))
                .bankAccountExternalId(UUID.randomUUID())
                .expenseType(ExpenseType.EXPENSE)
                .build();
    }

}