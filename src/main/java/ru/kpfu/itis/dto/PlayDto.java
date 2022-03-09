package ru.kpfu.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class PlayDto {
    private String current_play_game_id;
    private String deck_to_take_card_from;
    private String deck_to_shuffle;
}