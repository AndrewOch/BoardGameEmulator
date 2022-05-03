package ru.kpfu.itis.models.dtos;

import lombok.Data;

@Data
public class CurrencyDto {
    private Long currentEditGameId;
    private Long currentEditCurrencyId;
    private String currencyName;
    private String currencyDescription;
}
