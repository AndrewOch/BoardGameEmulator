package ru.kpfu.itis.models.form;

import lombok.*;
import ru.kpfu.itis.models.entities.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class GameForm {
    private String name;
    private String description;
    private User user;
}