package dev.vorstu.db.entities.films;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название изображение */
    private String imageName;

    /** Ссылка (URL) на изображение */
    private String imageUrl;

    /** Сущность фильма */
    @ManyToOne(targetEntity = Film.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    private Film film;
    /** Идентификатор фильма */
    @Column(name = "film_id")
    private Long filmId;
}
