package ru.kpfu.itis.models.entities;

import lombok.*;

import java.util.Stack;

@Data
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

    public Card takeCard() {
        Card card = deck.pop();
        waste.push(card);
        return card;
    }
}
