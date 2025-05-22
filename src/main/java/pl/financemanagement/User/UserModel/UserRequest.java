package pl.financemanagement.User.UserModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Email cannot be blank or empty")
    @Email(message = "Email is not correct")
    private String email;
    @NotBlank
    @Size(max = 64)
    private String name;
    @NotBlank(message = "Password cannot be blank or empty")
    @Size(min = 1)
    private String password;

}
