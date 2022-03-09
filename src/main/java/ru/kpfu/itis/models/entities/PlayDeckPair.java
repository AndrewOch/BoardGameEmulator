package ru.kpfu.itis.models.entities;

import lombok.*;

import java.util.Stack;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlayDeckPair {

    private Long deckId;
    private Stack<Card> deck;
    private Stack<Card> waste;

    public PlayDeckPair(Long deckId, Stack<Card> deck) {
        this.deckId = deckId;
        this.deck = deck;
        waste = new Stack<>();
    }
}
