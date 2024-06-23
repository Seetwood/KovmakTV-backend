package dev.vorstu.db.enums;

import lombok.Getter;

/**
 * Роли пользователей:
 * SUPER_USER - администратор
 * USER - обычный пользователь
 */
@Getter
public enum RoleUser {
    SUPER_USER,
    USER
}

