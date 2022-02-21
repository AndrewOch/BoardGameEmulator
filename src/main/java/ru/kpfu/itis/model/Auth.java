package ru.kpfu.itis.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private Long id;
    private User user;
    private String cookieValue;
}
