package pl.financemanagement.User.UserService;

import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.financemanagement.AppTools.AppTools;
import pl.financemanagement.JWToken.Service.JwtService;
import pl.financemanagement.PasswordTools.PasswordService;
import pl.financemanagement.User.UserModel.*;
import pl.financemanagement.User.UserModel.exceptions.EmailAlreadyInUseException;
import pl.financemanagement.User.UserModel.exceptions.UserExistsException;
import pl.financemanagement.User.UserModel.exceptions.UserNotFoundException;
import pl.financemanagement.User.UserRepository.UserAccountRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static pl.financemanagement.User.UserModel.UserRole.USER;
import static pl.financemanagement.User.UserModel.UsersMapper.mapToUserDto;

@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final PasswordService passwordService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public UserServiceImpl(JwtService jwtService,
                           UserAccountRepository userAccountRepository,
                           PasswordService passwordService,
                           KafkaTemplate<String, Object> kafkaTemplate) {
        this.jwtService = jwtService;
        this.userAccountRepository = userAccountRepository;
        this.passwordService = passwordService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) throws JOSEException {
        LOGGER.info("Attempting to create user with email: {}", userRequest.getEmail());
        Optional<UserAccount> existingUser = userAccountRepository.findUserByEmail(userRequest.getEmail());

        if (existingUser.isPresent()) {
            LOGGER.info("Cannot create user with email {} because already exists", userRequest.getEmail());
            throw new UserExistsException("User with email " + userRequest.getEmail() + " exists");
        }

        UserAccount userToSave = mapToUserAccount(userRequest);
        UserAccount savedUser = userAccountRepository.save(userToSave);

        String token = jwtService.generateUserToken(userRequest.getEmail(), USER.getRole());

        return new UserResponse(true, mapToUserDto(savedUser), token);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateRequest userRequest, String email) throws JOSEException {
        LOGGER.info("Attempting to update user with email: {}", email);
        UserAccount userAccount = userAccountRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " does not exist"));

        if (AppTools.isNotBlank(userRequest.getNewEmail()) && isEmailUniqueForOtherUsers(userRequest.getNewEmail(), userAccount)) {
            throw new EmailAlreadyInUseException("Email " + userRequest.getNewEmail() + " is already in use.");
        }

        if (userRequest.getNewEmail() != null) {
            LOGGER.info("Change email from {} to {}", userAccount.getEmail(), userRequest.getNewEmail());
            userAccount.setEmail(userRequest.getNewEmail());
        }
        if (userRequest.getNewName() != null) {
            LOGGER.info("Change name from {} to {}", userAccount.getName(), userRequest.getNewName());
            userAccount.setName(userRequest.getNewName());
        }

        userAccount.setModifyOn(Instant.now());
        UserAccount savedUser = userAccountRepository.save(userAccount);
        String token = getGeneratedUserToken(savedUser);

        return new UserResponse(true, mapToUserDto(savedUser), token);
    }

    @Override
    public UserResponse getBasicData(String email) {
        UserAccount user = userAccountRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
        return new UserResponse(true, mapToUserDto(user));
    }

    @Override
    public UserResponse getUserById(long id, String email) {
        UserAccount user = userAccountRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        return new UserResponse(true, mapToUserDto(user));
    }

    @Override
    @Transactional
    public UserDeleteResponse deleteUser(UUID externalId, String email) {
        UserAccount userAccount = userAccountRepository.findUserByEmailAndExternalId(email, externalId)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " does not exist"));

        kafkaTemplate.send("user_account_delete_topic", userAccount);

        LOGGER.info("User with email {} is successfully removed", userAccount.getName());

        return new UserDeleteResponse(true, "User deleted.");
    }

    private boolean isEmailUniqueForOtherUsers(String newEmail, UserAccount currentAccount) {
        Optional<UserAccount> userWithSameEmail = userAccountRepository.findUserByEmail(newEmail);
        return userWithSameEmail.isEmpty() || userWithSameEmail.get().getId() == currentAccount.getId();
    }

    private UserAccount mapToUserAccount(UserRequest userRequest) {
        UserAccount userToSave = new UserAccount();
        userToSave.setEmail(userRequest.getEmail());
        userToSave.setName(userRequest.getName());
        userToSave.setCreatedOn(Instant.now());
        userToSave.setExternalId(UUID.randomUUID());
        userToSave.setUserRole(USER);
        userToSave.setPassword(passwordService.hashPassword(userRequest.getPassword()));
        return userToSave;
    }

    private String getGeneratedUserToken(UserAccount savedUser) throws JOSEException {
        return jwtService.generateUserToken(savedUser.getEmail(), USER.getRole());
    }

}
