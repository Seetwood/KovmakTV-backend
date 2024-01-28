package dev.vorstu.db.services.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.repositories.CommentRepository;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.CommentDto;
import dev.vorstu.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void createComment(Long reviewId, Long parentCommentId, CommentDto commentDto, Principal sender) {
        AuthUserEntity senderId = userService.getUserByUsername(sender.getName());
        Comment newComment = new Comment(senderId.getId(), reviewId, commentDto.getTextComment(), parentCommentId);
        commentRepository.save(newComment);

        messagingTemplate.convertAndSend("/topic/comments/" + reviewId, "Новый комментарий добавлен");
    }
}