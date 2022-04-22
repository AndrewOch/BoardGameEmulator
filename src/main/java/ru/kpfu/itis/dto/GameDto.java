package ru.kpfu.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class GameDto {
    private String currentEditGameId;
    private String currentPlayGameId;

    private String gameName;
    private String gameDescription;
}
