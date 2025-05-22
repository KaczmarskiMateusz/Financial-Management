package pl.financemanagement.User.UserModel;

public class UsersMapper {

    public static UserDto mapToUserDto(UserAccount userAccount) {
        return UserDto.builder()
                .email(userAccount.getEmail())
                .name(userAccount.getName())
                .externalId(userAccount.getExternalId())
                .userRole(userAccount.getUserRole())
                .build();
    }

}
