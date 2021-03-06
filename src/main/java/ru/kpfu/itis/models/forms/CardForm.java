package ru.kpfu.itis.models.forms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class CardForm {
    private String name;
    private String description;
    private Long currencyId;
    private Integer value;
    private Long deckId;
    private Integer copiesCount;
}