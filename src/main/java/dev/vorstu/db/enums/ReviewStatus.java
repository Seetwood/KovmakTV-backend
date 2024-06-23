package dev.vorstu.db.enums;

import lombok.Getter;

/**
 * Статусы рецензий пользователей:
 * CREATED - создан
 * VERIFIED - одобрен
 * REJECTED - отклонен
 */
@Getter
public enum ReviewStatus {
   CREATED,
   VERIFIED,
   REJECTED
}
