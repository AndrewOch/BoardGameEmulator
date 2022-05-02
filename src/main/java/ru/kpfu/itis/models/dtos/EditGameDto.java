package ru.kpfu.itis.models.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditGameDto {
    private Long currentEditGameId;
    private String gameName;
    private String gameDescription;
}