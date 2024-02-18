package dev.vorstu.db.services.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.repositories.CommentRepository;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.CommentDto;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;

    public void createComment(Long reviewId, Long parentCommentId, CommentDto commentDto, Principal sender) {
        AuthUserEntity senderId = userService.getUserByUsername(sender.getName());

        Comment newComment = CommentMapper.MAPPER.toEntity(commentDto);
        newComment.setSenderId(senderId.getId());
        newComment.setReviewId(reviewId);
        newComment.setParentCommentId(parentCommentId);
        commentRepository.save(newComment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment is not found"));
        commentRepository.deleteById(comment.getId());
    }

}