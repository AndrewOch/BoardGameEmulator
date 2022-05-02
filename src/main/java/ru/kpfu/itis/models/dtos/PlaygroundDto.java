package ru.kpfu.itis.models.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaygroundDto {
    private Long currentEditGameId;
    private MultipartFile playGroundFile;
}
