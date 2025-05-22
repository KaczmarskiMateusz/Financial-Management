package pl.financemanagement.User.UserController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.financemanagement.User.UserModel.UserRequest;
import pl.financemanagement.User.UserService.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean(name = "userServiceImpl")
    private UserService service;
    @MockBean(name = "userServiceDemo")
    private UserService serviceDemo;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .email("example@email.pl")
                .name("name")
                .password("password")
                .build();

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserBasicData() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void deleteUser() {
    }
}