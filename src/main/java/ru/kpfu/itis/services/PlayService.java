package ru.kpfu.itis.services;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.Card;
import ru.kpfu.itis.models.entities.Deck;
import ru.kpfu.itis.models.entities.PlayDeckPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Component
public class PlayService {

    private ArrayList<PlayDeckPair> deckPairs;

    public void setDecks(List<Deck> decks){
        deckPairs = new ArrayList<>();
        for (Deck deck : decks) {
            List<Card> cardCopies = new ArrayList<>();
            PlayDeckPair playDeckPair = new PlayDeckPair();
            playDeckPair.setDeckId(deck.getId());
            Stack<Card> cards = new Stack<>();
            deck.getCards().forEach(card -> {
                for (int i = 0; i < card.getCopiesCount() - 1; i++) {
                    cardCopies.add(card);
                }
            });
            cards.addAll(deck.getCards());
            cards.addAll(cardCopies);
            deck.setCards(cards);
            playDeckPair.setDeck(cards);
            playDeckPair.setWaste(new Stack<>());
            deckPairs.add(playDeckPair);
        }
    }

    public PlayDeckPair getDeckById(Long deckId) {
        for (PlayDeckPair deck : deckPairs) {
            if (deck.getDeckId().equals(deckId)) {
                return deck;
            }
        }
        return null;
    }

    public void shuffleDeck(Long deckId) {
        PlayDeckPair deck = getDeckById(deckId);
        assert deck != null;
        Stack<Card> cardsFromWaste = deck.getWaste();
        Stack<Card> cardsFromDeck = deck.getDeck();
        Stack<Card> cards = new Stack<>();
        cards.addAll(cardsFromWaste);
        cards.addAll(cardsFromDeck);
        deck.getWaste().clear();
        Collections.shuffle(cards);
        deck.setDeck(cards);
    }
}
