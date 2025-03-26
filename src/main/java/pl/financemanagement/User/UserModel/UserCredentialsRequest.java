package pl.financemanagement.User.UserModel;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentialsRequest {

    @NotBlank(message = "Email cannot be blank")
    @JsonAlias("login")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

}
