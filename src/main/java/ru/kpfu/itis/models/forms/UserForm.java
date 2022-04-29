package ru.kpfu.itis.models.forms;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {
    private String username;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "UserForm{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}