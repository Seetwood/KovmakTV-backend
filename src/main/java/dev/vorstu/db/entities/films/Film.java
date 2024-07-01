package dev.vorstu.db.entities.films;

import dev.vorstu.db.entities.reviews.Review;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "films")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название */
    @NonNull
    private String name;
    /** Год релиза */
    private Integer yearOfRelease;

    /** Продолжительность в минутах */
    private Integer duration;

    /** Описание */
    @Column(columnDefinition = "TEXT")
    private String description;
    /** Страна производства */
    private String country;

    /** Жанр фильма */
    @ManyToOne
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;
    @Column(name = "genre_id")
    private Long genreId;

    /** Список изображений */
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Image> imagesList;

    /** Список видео */
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Video> videosList;

    /** Рецензии */
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    /** Рейтинг */
    @Builder.Default
    private Integer rating = 0;
}