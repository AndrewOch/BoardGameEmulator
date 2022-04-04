package ru.kpfu.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class EditGameDto {
    private String currentEditGameId;
    private String current_edit_currency_id;
    private String current_edit_deck_id;
    private String deck_to_show_cards;
    private String current_edit_card_id;
}
