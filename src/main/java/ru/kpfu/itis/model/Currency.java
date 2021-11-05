package ru.kpfu.itis.model;

import java.sql.Timestamp;

public class Currency {

    private Long id;
    private Long gameId;
    private String name;
    private String description;
    private Timestamp createdAt;

    public Currency(Long id, Long gameId, String name, String description, Timestamp createdAt) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Currency() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
