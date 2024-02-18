package dev.vorstu.db.entities.films;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String traillerName;
    private String traillerUrl;

    @ManyToOne(targetEntity = Film.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    private Film film;
    @Column(name = "film_id")
    private Long filmId;

    public Video(Long filmId, String traillerName, String traillerUrl) {
        this.filmId = filmId;
        this.traillerName = traillerName;
        this.traillerUrl = traillerUrl;
    }

}
