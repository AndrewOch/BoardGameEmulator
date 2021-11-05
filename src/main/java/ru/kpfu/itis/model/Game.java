package ru.kpfu.itis.model;

import java.util.ArrayList;

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

    public Game(Long id, String name, String description, ArrayList<Deck> decks, ArrayList<Currency> currencies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.decks = decks;
        this.currencies = currencies;
    }

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }
}
