package dev.vorstu.dto;

import dev.vorstu.db.enums.ReviewStatus;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDto {
    /** Идентификатор рецензии */
    private Long id;

    /** Идентификатор фильма, к которому пишется рецензия */
    private Long filmId;

    /** Идентификатор пользователя, пишущий рецензию */
    private Long userId;

    /** Имя пользователя */
    private String name;

    /** Фамилия пользователя */
    private String surname;

    /** Название фильма */
    private String filmName;

    /** Заголовок */
    private String header;

    /** Текст */
    private String textReview;

    /** Статус рецензии */
    private ReviewStatus status;

    /** Комментарии к рецензии */
    private List<CommentDto> comments;
}
