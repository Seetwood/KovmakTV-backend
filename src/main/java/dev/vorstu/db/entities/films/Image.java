package dev.vorstu.db.entities.films;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    private String imageUrl;

    @ManyToOne(targetEntity = Film.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    private Film film;
    @Column(name = "film_id")
    private Long filmId;

    public Image(Long filmId, String imageName, String imageUrl) {
        this.filmId = filmId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}
