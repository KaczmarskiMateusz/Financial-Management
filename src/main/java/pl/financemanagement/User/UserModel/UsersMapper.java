package pl.financemanagement.User.UserModel;

public class UsersMapper {

    public static UserDto userDtoMapper(UserAccount userAccount) {
       return UserDto.buildUserDto(userAccount.getEmail(), userAccount.getName(),
              userAccount.getExternalId(), userAccount.getUserRole());
    }

}
