package ru.kpfu.itis.models.dtos;

import lombok.Data;

@Data
public class CardDto {
    private Long deckToShowCards;
    private Long currentEditCardId;
    private String cardName;
    private String cardDescription;
    private Long cardCurrencyId;
    private Integer cardValue;
    private Integer cardCopiesCount;
}
