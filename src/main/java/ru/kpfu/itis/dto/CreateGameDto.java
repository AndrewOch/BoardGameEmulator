package ru.kpfu.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString

public class CreateGameDto {
    private String choose_game;
    private String game_name;
    private String game_description;
    private String choose_currency;
    private String currency_name;
    private String currency_description;
    private String choose_deck;
    private String deck_name;
    private String deck_description;
    private String choose_card;
    private String card_name;
    private String card_description;
    private String currency_of_a_card;
    private String card_value;
}
