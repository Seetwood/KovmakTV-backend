package dev.vorstu.db.entities.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.enums.ReviewStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Сущность фильма */
    @ManyToOne(targetEntity = Film.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    /** Идентификатор фильма */
    @Column(name = "film_id")
    private Long filmId;

    /** Сущность пользователя */
    @ManyToOne(targetEntity = AuthUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AuthUserEntity user;
    /** Идентификатор пользователя */
    @Column(name = "user_id")
    private Long userId;

    /** Заголовок */
    private String header;

    /** Текст рецензии */
    @Column(columnDefinition = "TEXT")
    private String textReview;

    /** Список комментариев */
    @OneToMany(mappedBy = "review",  cascade = CascadeType.ALL)
    private List<Comment> commentList;

    /** Рейтинг рецензии */
    private String rating;

    /** Статус */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;
}
