package ru.kpfu.itis.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kpfu.itis.models.dtos.SignUpDto;
import ru.kpfu.itis.services.interfaces.UsersService;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SignUpDto signUpDto = (SignUpDto) obj;
        ValidationUtils.rejectIfEmpty(errors, "username", "username", "Не указано Имя");
        ValidationUtils.rejectIfEmpty(errors, "email", "email", "Не указан Логин");
        ValidationUtils.rejectIfEmpty(errors, "password", "password", "Не указан Пароль");
        ValidationUtils.rejectIfEmpty(errors, "consent", "consent", "Не указано Согласие");

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(signUpDto.getEmail()).matches())) {
            errors.reject("email", "Неправильный email");
        }

        if (!usersService.usernameDoesntExist(signUpDto.getEmail())) {
            errors.reject("email", "Пользователь с таким логином существует");
        }

        if (!signUpDto.getPassword().equals(signUpDto.getRetype_password())) {
            errors.reject("confirmPassword", "Пароли не совпадают");
        }
    }
}