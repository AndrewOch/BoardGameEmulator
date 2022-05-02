package ru.kpfu.itis.models.forms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class DeckForm {
    private String name;
    private String description;
}