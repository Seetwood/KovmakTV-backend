package dev.vorstu.controllers.reviews;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.enums.ReviewStatus;
import dev.vorstu.db.services.reviews.ReviewService;
import dev.vorstu.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

/**
 * Контроллер, предназначенный для возаимодействия пользователей с рецензиями
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class ReviewController {
    private final ReviewService reviewService;

    @Transactional
    @GetMapping("reviews/")
    public List<ReviewDto> getAllReviewsCreated() {
        return reviewService.getAllReviewsCreated();
    }

    @GetMapping("reviews/reviews-user/{reviewId}")
    public ReviewDto getPageReview(@PathVariable Long reviewId) {
        return reviewService.getPageReview(reviewId);
    }

    @GetMapping("reviews/film/{id}")
    public Page<ReviewDto> getReviewByFilm(Film film,
                                           @RequestParam(defaultValue = "0") int pageIndex,
                                           @RequestParam(defaultValue = "15") int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return reviewService.getReviewByFilm(film, pageable);
    }

    @GetMapping("profile/reviews/{status}")
    public List<ReviewDto> getReviewByUser(Principal user, @PathVariable ReviewStatus status) {
        return reviewService.getReviewByUserStatus(user, status);
    }

    @PostMapping("reviews/film/{filmId}")
    public void createReview(@PathVariable Long filmId, Principal principal, @RequestBody ReviewDto reviewDto) {
        reviewService.createReview(filmId, principal, reviewDto);
    }

    @PutMapping("profile/review/{userId}/{filmId}")
        public Long updateReviewText(@PathVariable Long filmId, @PathVariable Long userId, Principal principal, @RequestBody ReviewDto newTextReview) {
        reviewService.updateReviewText(filmId, userId, principal, newTextReview);
        return userId;
    }
    @PreAuthorize("hasAnyAuthority('SUPER_USER')")
    @PutMapping("reviews/{filmId}/{userId}/{status}")
    public Long reviewApproved(@PathVariable Long filmId, @PathVariable Long userId, @PathVariable ReviewStatus status) {
        reviewService.updateReviewStatus(filmId, userId, status);
        return userId;
    }

    @DeleteMapping("reviews/{userId}/{filmId}")
    public void deleteReview(@PathVariable Long userId, @ PathVariable Long filmId) {
        reviewService.deleteReview(userId, filmId);
    }
}
