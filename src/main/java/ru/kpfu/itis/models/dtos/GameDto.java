package ru.kpfu.itis.models.dtos;

import lombok.*;

@Data
public class GameDto {
    private Long currentEditGameId;
    private Long currentPlayGameId;
    private String gameName;
    private String gameDescription;
}
