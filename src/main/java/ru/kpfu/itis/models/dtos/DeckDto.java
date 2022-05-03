package ru.kpfu.itis.models.dtos;

import lombok.Data;

@Data
public class DeckDto {
    private Long currentEditGameId;
    private Long currentEditDeckId;
    private String deckName;
    private String deckDescription;
}
