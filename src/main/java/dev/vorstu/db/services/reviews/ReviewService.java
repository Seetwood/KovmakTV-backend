package dev.vorstu.db.services.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.enums.ReviewStatus;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.db.repositories.ReviewRepository;
import dev.vorstu.db.services.films.FilmService;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.CommentDto;
import dev.vorstu.dto.ReviewDto;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.CommentMapper;
import dev.vorstu.mappers.ReviewMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис для взаимодействия с жанрами фильмов
 */
@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    /**
     * Найти рецензии по идентификатору фильма и пользователя
     * @param filmId - Идентификатор фильма
     * @param userId - Идентификатор пользователя, написавший рецензию
     * @return - Найденная рецензия
     */
    public Review findByReviewId(Long filmId, Long userId){
         return reviewRepository.findByFilmIdAndUserId(filmId, userId)
                .orElseThrow(() -> new NotFoundException("Review is not found"));
    }

    /**
     * Получение полной информации о рецензии, включая вложенные комментарии
     * @param reviewId - Идентификатор рецензии
     * @return - Страница с рецензией
     */
    @Transactional
    public ReviewDto getPageReview(Long reviewId) {
        Review review = reviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review is not found"));
        ReviewDto reviewDto = ReviewMapper.MAPPER.toDto(review);
        List<CommentDto> comments = getAllCommentsForReview(review.getCommentList(), null);
        reviewDto.setComments(comments);

        return reviewDto;
    }

    /**
     * Получение вложенных комментариев к рецензии
     * @param comments - Список комментариев
     * @param parentCommentId - Идентификатор родительского комментария
     * @return - Список вложенных комментариев
     */
    private List<CommentDto> getAllCommentsForReview(List<Comment> comments, Long parentCommentId) {
        List<CommentDto> nestedComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (Objects.equals(comment.getParentCommentId(), parentCommentId)) {
                CommentDto commentDto = CommentMapper.MAPPER.toDto(comment);
                List<CommentDto> childComments = getAllCommentsForReview(comment.getChildComments(), comment.getId());
                commentDto.setChildComments(childComments);
                nestedComments.add(commentDto);
            }
        }
        return nestedComments;
    }

    /**
     *  Получение списка рецензий со статусом "СОЗДАН"
     * @return Список рецензий
     */
    @Transactional
    public List<ReviewDto> getAllReviewsCreated() {
        return reviewRepository.findAllByStatus(ReviewStatus.CREATED).stream()
                .map(ReviewMapper.MAPPER::toDto)
                .collect(Collectors.toList());
    }

    /** todo
     * Получение страницы с рецензией по идентифкатору фильма
     * @param film
     * @param pageable
     * @return
     */
    @Transactional
    public Page<ReviewDto> getReviewByFilm(Film film, Pageable pageable) {
        return reviewRepository.findAllByFilmAndStatus(film, ReviewStatus.VERIFIED, pageable)
                .map(ReviewMapper.MAPPER::toDto);
    }

    /**
     * todo
     * @param user
     * @param status
     * @return
     */
    @Transactional
    public List<ReviewDto> getReviewByUserStatus(Principal user, ReviewStatus status) {
        String username = user.getName();
        return reviewRepository.findAllByUserUsernameAndStatusIn(username, Collections.singletonList(status)).stream()
                .map(ReviewMapper.MAPPER::toDto)
                .collect(Collectors.toList());
    }

    /** todo
     * Создание новой рецензнии
     * @param filmId - Идентификатор фильма, к которому рецензия пишется
     * @param principal
     * @param reviewDto
     */
    @Transactional
    public void createReview(Long filmId, Principal principal, ReviewDto reviewDto) {
        AuthUserEntity currentUser = userService.getUserByUsername(principal.getName());
        Review review = ReviewMapper.MAPPER.toEntity(reviewDto);
        review.setFilmId(filmId);
        review.setUserId(currentUser.getId());
        review.setStatus(ReviewStatus.CREATED);
        reviewRepository.save(review);
    }

    /** todo
     * Изменение текста рецензии
     * @param filmId - Идентификатор фильма
     * @param userId - Идентификатор пользователя, написавшего рецензию
     * @param principal
     * @param newTextReview - Новая рецензия
     */
    public void updateReviewText(Long filmId, Long userId, Principal principal, ReviewDto newTextReview) {
        Review review = this.findByReviewId(filmId, userId);
        AuthUserEntity user = review.getUser();
        AuthUserEntity currentUser = userService.getUserByUsername(principal.getName());
        if (currentUser.getId().equals(user.getId())) {
            ReviewMapper.MAPPER.updateReview(newTextReview, review);
            review.setStatus(ReviewStatus.CREATED);
            reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("Редактирование рецензии разрешено только для пользователя, который ее создал");
        }
    }

    /**
     * Обновление статуса рецензии
     * @param filmId - Идентификатор фильма
     * @param userId - Идентификатор пользователя
     * @param status - Новый статус
     */
    @PreAuthorize("hasAuthority('SUPER_USER')")
    public void updateReviewStatus(Long filmId, Long userId, ReviewStatus status) {
        Review review = findByReviewId(filmId, userId);
        review.setStatus(status);
        reviewRepository.save(review);
    }

    /**
     * Удаление рецензии
     * @param filmId - Идентификатор фильма
     * @param userId - Идентификатор пользователя
     */
    @Transactional
    public void deleteReview(Long filmId, Long userId) {
        Review review = findByReviewId(filmId, userId);
        reviewRepository.deleteById(review.getId());
    }
}
