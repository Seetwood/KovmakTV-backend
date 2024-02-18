    package dev.vorstu.db.repositories;

    import dev.vorstu.db.entities.films.Film;
    import dev.vorstu.db.entities.reviews.Review;
    import dev.vorstu.db.enums.ReviewStatus;
    import org.jetbrains.annotations.NotNull;
    import org.springframework.beans.PropertyValues;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.EntityGraph;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.DoubleStream;

    @Repository
    public interface ReviewRepository extends JpaRepository<Review, Long> {
        Page<Review> findAllByFilmAndStatus(Film film, ReviewStatus status, Pageable pageable);
        List<Review>  findAllByStatus(ReviewStatus status);
        List<Review> findAllByUserUsernameAndStatusIn(String username, List<ReviewStatus> reviewStatuses);
        Optional<Review> findByFilmIdAndUserId(Long filmId, Long userId);
        @EntityGraph(attributePaths = "commentList")
        Optional<Review> findReviewById(Long reviewId);

    }