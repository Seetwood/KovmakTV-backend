package dev.vorstu.dto;

import lombok.Data;

/**
 * Дто для регистрации нового пользователя
 */
@Data
public class UserSignUpDto {
    /** Идентификатор пользователя */
    private Long id;

    /** Никнейм */
    private String username;

    /** Пароль */
    private String password;

    /** Имя */
    private String name;

    /** Фамилия */
    private String surname;
}