package ru.kpfu.itis.model;

import java.util.ArrayList;

public class Deck {

    private Integer id;
    private String name;
    private String description;
    private ArrayList<Card> cards;

    public Deck(Integer id, String name, String description, ArrayList<Card> cards) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cards = cards;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
