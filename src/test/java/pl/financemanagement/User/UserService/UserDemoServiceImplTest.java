package pl.financemanagement.User.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.financemanagement.User.UserModel.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserDemoServiceImplTest {

    //TODO refactor tests!!
    private final static String NAME = "Demo";
    private final static String EMAIL = "demo@financialapp.com";
    private final static String UPDATED_USER_EMAIL = "demo1@financialapp.com";
    private final static String PASSWORD = "password";
    private final static UUID EXTERNAL_ID = UUID.fromString("f9969a5d-55d2-4e31-83e1-5759500a1e6d");

    @InjectMocks
    private UserDemoServiceImpl userDemoService;

    @Test
    void createUser() {
        UserResponse expectedUserResponse = new UserResponse(true, buildUserDto(EXTERNAL_ID, NAME, EMAIL));

        assertThat(userDemoService.createUser(buildUserRequest(NAME, EMAIL, PASSWORD)))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @Test
    void updateUser() {
        UserResponse expectedUserResponse =
                new UserResponse(true, buildUserDto(EXTERNAL_ID, NAME, UPDATED_USER_EMAIL));

        assertThat(userDemoService.updateUser(buildUserUpdateRequest(UPDATED_USER_EMAIL, NAME), EMAIL))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"example@email.com", "example1@email.com", "example3@email.com"})
    void getBasicDataWhenUserExists(String email) {
        UserErrorResponse expectedUserResponse = new UserErrorResponse(false, "User not found");

        assertThat(userDemoService.getBasicData(email))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @Test
    void getBasicDataWhenUserDoesNotExist() {
        UserResponse expectedUserResponse = new UserResponse(true, buildUserDto(EXTERNAL_ID, NAME, EMAIL));

        assertThat(userDemoService.getBasicData(EMAIL))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @Test
    void getUserByIdWithCorrectId() {
        UserResponse expectedUserResponse = new UserResponse(true, buildUserDto(EXTERNAL_ID, NAME, EMAIL));

        assertThat(userDemoService.getUserById(2, EMAIL))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 4, 5, 6})
    void getUserByIdWithWrongId(int id) {
        UserErrorResponse expectedUserResponse = new UserErrorResponse(false, "User not found");

        assertThat(userDemoService.getUserById(id, "exampleEmail"))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    @Test
    void deleteUser() {
        UserDeleteResponse expectedUserResponse = new UserDeleteResponse(true, "User deleted.");

        assertThat(userDemoService.deleteUser(EXTERNAL_ID, EMAIL))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedUserResponse);
    }

    private UserDto buildUserDto(UUID externalId, String name, String email) {
        return UserDto.builder()
                .email(email)
                .name(name)
                .externalId(externalId)
                .build();
    }

    private UserUpdateRequest buildUserUpdateRequest(String email, String name) {
        UserUpdateRequest userRequest = new UserUpdateRequest();
        userRequest.setNewEmail(email);
        userRequest.setNewName(name);
        return userRequest;
    }

    private UserRequest buildUserRequest(String name, String email, String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        return userRequest;
    }


}