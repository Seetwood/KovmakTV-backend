package dev.vorstu.db.entities.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.enums.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Film.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    @Column(name = "film_id")
    private Long filmId;

    @ManyToOne(targetEntity = AuthUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AuthUserEntity user;
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "review",  cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @Column(columnDefinition = "TEXT")
    private String textReview;
    private String rating;
    private String header;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    public Review(Long filmId, Long userId, String header, String textReview, ReviewStatus status) {
        this.filmId = filmId;
        this.userId = userId;
        this.header = header;
        this.textReview = textReview;
        this.status = status;
    }
}
