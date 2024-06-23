package dev.vorstu.dto;

import lombok.Data;

/**
 * Краткая информация о пользователе
 */
@Data
public class ShortUserDto {
    /** Идентификатор пользователя */
    private Long id;

    /** Никнейм пользователя */
    private String username;
}
