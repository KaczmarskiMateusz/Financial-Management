package pl.financemanagement.User.UserController;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.financemanagement.ApplicationConfig.DemoResolver.DemoResolver;
import pl.financemanagement.User.UserModel.UserDeleteResponse;
import pl.financemanagement.User.UserModel.UserRequest;
import pl.financemanagement.User.UserModel.UserResponse;
import pl.financemanagement.User.UserModel.UserUpdateRequest;
import pl.financemanagement.User.UserService.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends DemoResolver<UserService> {

    public UserController(@Qualifier("userServiceImpl") UserService service,
                          @Qualifier("userServiceDemo") UserService demoService) {
        super(service, demoService);
    }

    @PostMapping("/register")
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws JOSEException {
        return ResponseEntity.ok(service().createUser(userRequest));
    }

    @PutMapping("/me")
    ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest userRequest,
                                            Principal principal) throws JOSEException {
        return ResponseEntity.ok(resolveService(principal.getName()).updateUser(userRequest, principal.getName()));
    }

    @GetMapping()
    ResponseEntity<UserResponse> getUserBasicData(Principal principal) {
        return ResponseEntity.ok(resolveService(principal.getName()).getBasicData(principal.getName()));
    }

    @DeleteMapping("/{externalId}")
    ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable UUID externalId,
                                                  Principal principal) {
        return ResponseEntity.ok(resolveService(principal.getName()).deleteUser(externalId, principal.getName()));
    }

}
