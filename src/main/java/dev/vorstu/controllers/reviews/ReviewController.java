package dev.vorstu.controllers.reviews;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.enums.ReviewStatus;
import dev.vorstu.db.repositories.ReviewRepository;
import dev.vorstu.db.services.reviews.ReviewService;
import dev.vorstu.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("api/")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

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
    public void addReview(@PathVariable Long filmId, Principal principal, @RequestBody ReviewDto reviewDto) {
        reviewService.createReview(filmId, principal, reviewDto);
    }

    @PutMapping("profile/review/{userId}/{filmId}")
        public Long updateReviewText(@PathVariable Long filmId, @PathVariable Long userId, Principal principal, @RequestBody Review newTextReview) {
        reviewService.updateReviewText(filmId, userId, principal, newTextReview);
        return userId;
    }

    @PutMapping("reviews/{filmId}/{userId}/{status}")
    public Long reviewApproved(@PathVariable Long filmId, @PathVariable Long userId, @PathVariable ReviewStatus status, Principal principal) {
        reviewService.updateReviewStatus(filmId, userId, principal, status);
        return userId;
    }


//    @MessageMapping("/comments/{reviewId}")
//    @SendTo("/topic/comments/{reviewId}")
//    public String handleComment(@PathVariable Long reviewId, @RequestBody CommentDto commentDto) {
//        // Обработка комментария
//        return "Новый комментарий добавлен";
//    }
}
