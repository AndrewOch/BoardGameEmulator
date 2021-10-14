package ru.kpfu.itis.model;

public class Card {

    private Integer id;
    private Integer deckId;
    private Integer currencyId;
    private String name;
    private String description;
    private Integer value;

    public Card(Integer id, Integer deckId, Integer currencyId, String name, String description, Integer value) {
        this.id = id;
        this.deckId = deckId;
        this.currencyId = currencyId;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
