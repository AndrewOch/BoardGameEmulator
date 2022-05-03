package ru.kpfu.itis.models.forms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class CurrencyForm {
    private String name;
    private String description;
    private Long gameId;
}