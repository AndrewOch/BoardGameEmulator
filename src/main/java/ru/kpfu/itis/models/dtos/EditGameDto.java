package ru.kpfu.itis.models.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class EditGameDto {

    private String currentEditGameId;
    private String editingAction;

    private String gameName;
    private String gameDescription;

    private String currentEditCurrencyId;
    private String currencyName;
    private String currencyDescription;

    private String currentEditDeckId;
    private String deckName;
    private String deckDescription;

    private String deckToShowCards;
    private String currentEditCardId;
    private String cardName;
    private String cardDescription;
    private String cardCurrencyId;
    private String cardValue;
    private String cardCopiesCount;

    private MultipartFile playGroundFile;
}