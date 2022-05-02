package ru.kpfu.itis.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "createdat")
    private Timestamp createdAt;
}
