package dev.vorstu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Краткая информация о пользователе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUserDto {
    /** Идентификатор пользователя */
    private Long id;

    /** Никнейм пользователя */
    private String username;
}
