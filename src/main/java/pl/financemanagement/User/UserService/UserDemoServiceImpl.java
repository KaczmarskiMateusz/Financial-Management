package pl.financemanagement.User.UserService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.financemanagement.User.UserModel.*;

import java.util.UUID;

@Service
@Qualifier("userServiceDemo")
public class UserDemoServiceImpl implements UserService {

    private final static String USER_EMAIL = "demo@financialapp.com";
    private final static String UPDATED_USER_EMAIL = "demo1@financialapp.com";
    private final static String USER_NAME = "Demo";
    private final static UUID EXTERNAL_ID = UUID.fromString("f9969a5d-55d2-4e31-83e1-5759500a1e6d");

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return new UserResponse(true, buildUserDto());
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest userRequest, String email) {
        return new UserResponse(true, buildUserDto());
    }

    @Override
    public UserResponse getBasicData(String email) {
        return new UserResponse(true, buildUserDto());
    }

    @Override
    public UserResponse getUserById(long id, String email) {
        return new UserResponse(true, buildUserDto());
    }

    @Override
    public UserDeleteResponse deleteUser(UUID externalId, String email) {
        return new UserDeleteResponse(true, "User deleted.");
    }

    private UserDto buildUserDto() {
        return UserDto.builder()
                .email(USER_EMAIL)
                .name(USER_NAME)
                .externalId(EXTERNAL_ID)
                .build();
    }

}
