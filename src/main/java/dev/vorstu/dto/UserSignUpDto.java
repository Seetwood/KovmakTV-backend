package dev.vorstu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто для регистрации нового пользователя
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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