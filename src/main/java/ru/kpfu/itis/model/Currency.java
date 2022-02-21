package ru.kpfu.itis.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {

    private Long id;
    private Long gameId;
    private String name;
    private String description;
    private Timestamp createdAt;
}
