package ru.kpfu.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class PlayDto {
    private String currentPlayGameId;
    private String deckToTakeCardFrom;
    private String deckToShuffle;
}