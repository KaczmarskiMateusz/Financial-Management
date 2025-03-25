package pl.financemanagement.BankAccount.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import pl.financemanagement.BankAccount.Model.BankAccountRequest;
import pl.financemanagement.BankAccount.Model.Currency;
import pl.financemanagement.BankAccount.Service.BankAccountService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @MockBean(name = "bankAccountServiceImpl")
    private BankAccountService bankAccountService;

    @MockBean(name = "bankAccountServiceDemo")
    private BankAccountService bankAccountServiceDemo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        BankAccountRequest bankAccountRequest = buildBankAccountRequest();
        String accountRequest = objectMapper.writeValueAsString(bankAccountRequest);

        mockMvc.perform(post("/account")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountRequest))
                .andExpect(status().isOk());

        verify(bankAccountService).createAccount(any(), any());
    }

    @Test
    void createWithWrongField() throws Exception {
        BankAccountRequest bankAccountRequest = buildBankAccountRequest();
        bankAccountRequest.setAccountName(null);

        String accountRequest = objectMapper.writeValueAsString(bankAccountRequest);

        mockMvc.perform(post("/account")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        BankAccountRequest bankAccountRequest = buildBankAccountRequest();
        String accountRequest = objectMapper.writeValueAsString(bankAccountRequest);

        mockMvc.perform(put("/account")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountRequest))
                .andExpect(status().isOk());

        verify(bankAccountService).updateAccount(any(), any());
    }

    @Test
    void updateWithWrongValue() throws Exception {
        BankAccountRequest bankAccountRequest = buildBankAccountRequest();
        bankAccountRequest.setAccountName(null);

        String accountRequest = objectMapper.writeValueAsString(bankAccountRequest);

        mockMvc.perform(put("/account")
                        .with(jwt().authorities(new SimpleGrantedAuthority("user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAccountByExternalId() throws Exception {
        mockMvc.perform(get("/account/{externalId}", UUID.randomUUID())
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccount() throws Exception {
        mockMvc.perform(delete("/account/{externalId}", UUID.randomUUID())
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void bankAccountBalance() throws Exception {
        mockMvc.perform(get("/account/bankBalance/{externalId}", UUID.randomUUID())
                        .with(jwt().authorities(new SimpleGrantedAuthority("user"))))
                .andExpect(status().isOk());

        verify(bankAccountService).getBankAccountBalance(any(), any());
    }

    private BankAccountRequest buildBankAccountRequest() {
        return BankAccountRequest.builder()
                .accountBalance(BigDecimal.valueOf(1000))
                .accountName("test account name")
                .externalId(UUID.randomUUID())
                .currency(Currency.PLN)
                .build();
    }

}