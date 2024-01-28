package dev.vorstu.dto;

import lombok.Data;

import java.util.List;


@Data
public class CommentDto {
    private Long id;
    private Long reviewId;
    private Long parentCommentId;
    private String nameSender;
    private String surnameSender;
    private String textComment;
    private List<CommentDto> childComments;
}
