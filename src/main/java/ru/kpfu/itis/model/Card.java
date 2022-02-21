package ru.kpfu.itis.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    private Long id;
    private Long deckId;
    private Long currencyId;
    private String name;
    private String description;
    private Integer value;
}
