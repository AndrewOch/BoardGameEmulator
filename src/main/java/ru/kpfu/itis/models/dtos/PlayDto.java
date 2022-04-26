package ru.kpfu.itis.models.dtos;

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