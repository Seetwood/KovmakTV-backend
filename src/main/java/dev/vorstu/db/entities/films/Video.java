package dev.vorstu.db.entities.films;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "videos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название видео */
    private String traillerName;

    /** Ссылка (URL) на видео */
    private String traillerUrl;

    /** Сущность фильма */
    @ManyToOne(targetEntity = Film.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    private Film film;

    /** Идентификатор фильма */
    @Column(name = "film_id")
    private Long filmId;
}
