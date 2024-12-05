package dev.vorstu.db.services.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.repositories.CommentRepository;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.CommentDto;
import dev.vorstu.exception.BusinessException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * Сервис для взаимодействия с комментариями
 */
@Slf4j
@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    /**
     * Создание нового комментария
     * @param reviewId - Идентификатор рецензии
     * @param parentCommentId - Идентификатор родительского комментария
     * @param commentDto - Данные нового комментария
     * @param sender - Пользователь, отправивший комментарий
     */
    public void createComment(Long reviewId, Long parentCommentId, CommentDto commentDto, Principal sender) {
        try {
            AuthUserEntity senderId = userService.getUserByUsername(sender.getName());
            Comment newComment = CommentMapper.MAPPER.toEntity(commentDto);
            newComment.setSenderId(senderId.getId());
            newComment.setReviewId(reviewId);
            newComment.setParentCommentId(parentCommentId);
            commentRepository.save(newComment);
            log.info("Новый комментарий успешно создан: {}", commentDto);
        }
        catch (BusinessException e) {
            log.error("Ошибка при создании нового комментария: {}", e.getMessage());
            throw new BusinessException("Ошибка при создании нового комментария: {}", e.getMessage());
        }
    }

    /**
     * Удаление комментария
     * @param commentId - Идентификатор комментария
     */
    @Transactional
    public void deleteComment(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
            commentRepository.deleteById(comment.getId());
            log.info("Комментарий с идентификатором {} успешно удален.", commentId);
        }
        catch (BusinessException e) {
            log.error("Ошибка при удалении комментария: {}", e.getMessage());
            throw new BusinessException("Ошибка при удалении комментария: {}", e.getMessage());
        }
    }
}