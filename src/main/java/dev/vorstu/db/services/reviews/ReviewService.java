package dev.vorstu.db.services.reviews;


import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.enums.ReviewStatus;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.db.repositories.ReviewRepository;
import dev.vorstu.db.services.films.FilmService;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.CommentDto;
import dev.vorstu.dto.ReviewDto;
import dev.vorstu.mappers.CommentMapper;
import dev.vorstu.mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    UserService userService;
    @Autowired
    FilmService filmService;

    public Review findByReviewId(Long filmId, Long userId){
         return reviewRepository.findByFilmIdAndUserId(filmId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Рецензия не найдена"));
    }

    @Transactional
    public ReviewDto getPageReview(Long reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);

        ReviewDto reviewDto = ReviewMapper.MAPPER.toDto(review);
        reviewDto.setName(review.getUser().getName());
        reviewDto.setSurname(review.getUser().getSurname());
        reviewDto.setFilmName(review.getFilm().getName());

        List<CommentDto> comments = allCommentsForReview(review.getCommentList(), null);
        reviewDto.setComments(comments);

        return reviewDto;
    }

    private List<CommentDto> allCommentsForReview(List<Comment> comments, Long parentCommentId) {
        List<CommentDto> nestedComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (Objects.equals(comment.getParentCommentId(), parentCommentId)) {
                CommentDto commentDto = CommentMapper.MAPPER.toDto(comment);
                List<CommentDto> childComments = allCommentsForReview(comment.getChildComments(), comment.getId());
                commentDto.setChildComments(childComments);
                nestedComments.add(commentDto);
            }
        }
        return nestedComments;
    }

    @Transactional
    public List<ReviewDto> getAllReviewsCreated() {
        return reviewRepository.findAllByStatus(ReviewStatus.CREATED).stream()
                .map(review -> {
                    ReviewDto reviewDto = ReviewMapper.MAPPER.toDto(review);
                    reviewDto.setName(review.getUser().getName());
                    reviewDto.setSurname(review.getUser().getSurname());
                    reviewDto.setFilmName(review.getFilm().getName());
                    return reviewDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<ReviewDto> getReviewByFilm(Film film, Pageable pageable) {
        return reviewRepository.findAllByFilmAndStatus(film, ReviewStatus.VERIFIED, pageable)
                .map(review -> {
                    ReviewDto reviewDto = ReviewMapper.MAPPER.toDto(review);
                    reviewDto.setName(review.getUser().getName());
                    reviewDto.setSurname(review.getUser().getSurname());
                    reviewDto.setFilmName(review.getFilm().getName());
                    return reviewDto;
                });
    }

    @Transactional
    public List<ReviewDto> getReviewByUserStatus(Principal user, ReviewStatus status) {
        String username = user.getName();
        return reviewRepository.findAllByUserUsernameAndStatusIn(username, Collections.singletonList(status)).stream()
                .map(review -> {
                    ReviewDto reviewDto = ReviewMapper.MAPPER.toDto(review);
                    reviewDto.setName(review.getUser().getName());
                    reviewDto.setSurname(review.getUser().getSurname());
                    reviewDto.setFilmName(review.getFilm().getName());
                    reviewDto.setFilmId(review.getFilmId());
                    reviewDto.setUserId(review.getUserId());
                    return reviewDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void createReview(Long filmId, Principal principal, ReviewDto reviewDto) {
        AuthUserEntity currentUser = userService.getUserByUsername(principal.getName());
        Film film = filmService.findById(filmId);
        Review review = new Review(filmId,currentUser.getId(),reviewDto.getHeader(), reviewDto.getTextReview(), ReviewStatus.CREATED);
        review.setUser(currentUser);
        review.setFilm(film);
        reviewRepository.save(review);
    }

    public void updateReviewText(Long filmId, Long userId, Principal principal, Review newTextReview) {
        Review review = this.findByReviewId(filmId, userId);
        AuthUserEntity user = review.getUser();
        AuthUserEntity currentUser = userService.getUserByUsername(principal.getName());
        if (currentUser.getId().equals(user.getId()) && review.getStatus() == ReviewStatus.REJECTED) {
            review.setHeader(newTextReview.getHeader());
            review.setTextReview(newTextReview.getTextReview());
            review.setStatus(ReviewStatus.CREATED);
            reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("Редактирование рецензии разрешено только для пользователя, который ее создал");
        }
    }

    public void updateReviewStatus(Long filmId, Long userId, Principal principal, ReviewStatus status) {
        Review review = findByReviewId(filmId, userId);
        AuthUserEntity currentUser = userService.getUserByUsername(principal.getName());
        if (currentUser.getRole() != RoleUser.SUPER_USER) {
            throw new IllegalArgumentException("Недостаточно прав для изменения статуса рецензии");
        }
        review.setStatus(status);
        reviewRepository.save(review);
    }

}
