package dev.vorstu.controllers.reviews;

import dev.vorstu.db.services.reviews.CommentService;
import dev.vorstu.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Контроллер, предназначенный для возаимодействия пользователей с комментариями
 */
@RestController
@RequestMapping("api/reviews/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{reviewId}")
    public void createComment(@PathVariable Long reviewId, @RequestParam(required = false) Long parentCommentId, @RequestBody CommentDto commentDto, Principal sender) {
        commentService.createComment(reviewId, parentCommentId, commentDto, sender);
    }

    @DeleteMapping("{commentId}")
    public void deleteReview(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}