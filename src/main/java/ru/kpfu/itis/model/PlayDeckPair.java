package ru.kpfu.itis.model;

import java.util.Stack;

public class PlayDeckPair {

    private Long deckId;
    private Stack<Card> deck;
    private Stack<Card> waste;

    public PlayDeckPair(Long deckId, Stack<Card> deck) {
        this.deckId = deckId;
        this.deck = deck;
        waste = new Stack<>();
    }

    public PlayDeckPair() {
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Stack<Card> getDeck() {
        return deck;
    }

    public void setDeck(Stack<Card> deck) {
        this.deck = deck;
    }

    public Stack<Card> getWaste() {
        return waste;
    }

    public void setWaste(Stack<Card> waste) {
        this.waste = waste;
    }
}
