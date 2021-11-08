package ru.kpfu.itis.model;

public class Card {

    private Long id;
    private Long deckId;
    private Long currencyId;
    private String name;
    private String description;
    private Integer value;

    public Card(Long id, Long deckId, Long currencyId, String name, String description, Integer value) {
        this.id = id;
        this.deckId = deckId;
        this.currencyId = currencyId;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
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
