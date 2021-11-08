package ru.kpfu.itis.form;

public class CurrencyForm {
    private String name;
    private String description;
    private Long gameId;

    public CurrencyForm(String name, String description, Long gameId) {
        this.name = name;
        this.description = description;
        this.gameId = gameId;
    }

    public CurrencyForm() {
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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}