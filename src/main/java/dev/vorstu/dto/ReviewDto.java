package dev.vorstu.dto;

import dev.vorstu.db.enums.ReviewStatus;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDto {
    private Long id;
    private Long filmId;
    private Long userId;
    private String name;
    private String surname;
    private String filmName;
    private String header;
    private String textReview;
    private ReviewStatus status;

    private List<CommentDto> comments;

}
