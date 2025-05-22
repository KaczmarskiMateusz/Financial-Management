package pl.financemanagement.User.UserModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UsersMapperTest {

    private final static String NAME = "Demo";
    private final static String USER_EMAIL = "demo@financialapp.com";
    private final static String USER_NAME = "Demo";
    private final static String PASSWORD = "password";
    private final static UUID EXTERNAL_ID = UUID.fromString("f9969a5d-55d2-4e31-83e1-5759500a1e6d");

    @Test
    void mapToUserDto() {
        UserDto expected = buildUserDto();

        assertThat(UsersMapper.mapToUserDto(buildUserAccount()))
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("externalId")
                .isEqualTo(expected);
    }

    private UserDto buildUserDto() {
        return UserDto.builder()
                .email(USER_EMAIL)
                .name(USER_NAME)
                .externalId(EXTERNAL_ID)
                .build();
    }

    private static UserAccount buildUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(USER_EMAIL);
        userAccount.setName(NAME);
        userAccount.setPassword(PASSWORD);
        userAccount.setUserRole(UserRole.USER);
        userAccount.setExternalId(EXTERNAL_ID);
        return userAccount;
    }
}