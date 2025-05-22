package pl.financemanagement.User.UserModel;

import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String name;
    private UUID externalId;
    private UserRole userRole;

}
