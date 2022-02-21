package ru.kpfu.itis.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deck {

    private Long id;
    private String name;
    private String description;
    private ArrayList<Card> cards;
    private ArrayList<Card> waste;
}
