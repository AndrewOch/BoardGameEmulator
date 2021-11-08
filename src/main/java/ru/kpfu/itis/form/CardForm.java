package ru.kpfu.itis.form;

public class CardForm {
    private String name;
    private String description;
    private Long currencyId;
    private Integer value;
    private Long deckId;

    public CardForm(String name, String description, Long currencyId, Integer value, Long deckId) {
        this.name = name;
        this.description = description;
        this.currencyId = currencyId;
        this.value = value;
        this.deckId = deckId;
    }

    public CardForm() {
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }
}