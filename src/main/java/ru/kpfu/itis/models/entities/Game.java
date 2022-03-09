package ru.kpfu.itis.models.entities;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Game {

    private Long id;
    private String name;
    private String description;
    private ArrayList<Deck> decks;
    private ArrayList<Currency> currencies;

    public Game(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.decks = new ArrayList<>();
        this.currencies = new ArrayList<>();
    }
}
