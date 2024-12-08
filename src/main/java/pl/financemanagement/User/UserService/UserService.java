package pl.financemanagement.User.UserService;

import com.nimbusds.jose.JOSEException;
import org.springframework.stereotype.Service;
import pl.financemanagement.User.UserModel.*;

@Service("userService")
public interface UserService {

    UserResponse createUser(UserRequest userRequest) throws JOSEException;

    UserResponse updateUser(UserUpdateRequest userRequest, String email) throws JOSEException;

    UserResponse getBasicDataByEmail(String email);

    UserResponse getUserById(long id, String email) ;

    UserDeleteResponse deleteUser(String externalId, String email);

}
