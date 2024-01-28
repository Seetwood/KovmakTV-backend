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

    @Column(columnDefinition = "TEXT")
    private String traillerUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    private Film film;
    @Column(name = "film_id")
    private Long filmId;

    public Video(String traillerName, String traillerUrl) {
        this.traillerName = traillerName;
        this.traillerUrl = traillerUrl;

    }

    public Video(Long filmId, String traillerName, String traillerUrl) {
        this.filmId = filmId;
        this.traillerName = traillerName;
        this.traillerUrl = traillerUrl;
    }

}
