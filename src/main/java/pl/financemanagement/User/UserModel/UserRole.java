package pl.financemanagement.User.UserModel;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("user"),
    DEMO("demo"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
