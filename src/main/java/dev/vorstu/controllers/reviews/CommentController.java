package dev.vorstu.controllers.reviews;

import dev.vorstu.db.services.reviews.CommentService;
import dev.vorstu.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Контроллер, предназначенный для возаимодействия пользователей с комментариями
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/reviews/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{reviewId}")
    public void createComment(@PathVariable Long reviewId, @RequestParam(required = false) Long parentCommentId, @RequestBody CommentDto commentDto, Principal sender) {
        commentService.createComment(reviewId, parentCommentId, commentDto, sender);
    }

    @DeleteMapping("{commentId}")
    public void deleteReview(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}