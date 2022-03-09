package ru.kpfu.itis.models.entities;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Card {

    private Long id;
    private Long deckId;
    private Long currencyId;
    private String name;
    private String description;
    private Integer value;
}
