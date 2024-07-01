package dev.vorstu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    /** Идентификатор комментария */
    private Long id;

    /** Идентификатор рецензции */
    private Long reviewId;

    /** Идентификатор родительского комментария */
    private Long parentCommentId;

    /** Имя комментатора */
    private String nameSender;

    /** Фамилия комментатора */
    private String surnameSender;

    /** Текст комментария */
    private String textComment;

    /** Дочерние комментарии */
    private List<CommentDto> childComments;
}
