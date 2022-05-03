package ru.kpfu.itis.models.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class SignUpDto {
    private String username;
    private String email;
    private String password;
    private String retype_password;
}
