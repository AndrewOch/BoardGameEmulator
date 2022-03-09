package ru.kpfu.itis.models.entities;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Currency {

    private Long id;
    private Long gameId;
    private String name;
    private String description;
    private Timestamp createdAt;
}
