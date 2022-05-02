package ru.kpfu.itis.models.dtos;

import lombok.*;

@Data
public class AuthDto {
    private String login;
    private String password;
}