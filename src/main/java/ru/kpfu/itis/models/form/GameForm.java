package ru.kpfu.itis.models.form;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class GameForm {
    private String name;
    private String description;
}