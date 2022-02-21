package ru.kpfu.itis.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Timestamp createdAt;
}
