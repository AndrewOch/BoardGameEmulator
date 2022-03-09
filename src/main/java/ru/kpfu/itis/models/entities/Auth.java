package ru.kpfu.itis.models.entities;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Auth {
    private Long id;
    private User user;
    private String cookieValue;
}
