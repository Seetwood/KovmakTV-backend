package dev.vorstu.db.entities.films;

import dev.vorstu.db.entities.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int yearOfRelease;
    private int duration;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String country;

    @ManyToOne
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;
    @Column(name = "genre_id")
    private Long genreId;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Image> imagesList;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Video> videosList;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    public Film(String name, int yearOfRelease, int duration, String description, String country, Long genreId) {
        this.name = name;
        this.yearOfRelease = yearOfRelease;
        this.duration = duration;
        this.description = description;
        this.country = country;
        this.genreId = genreId;
    }
    public Film(String name, int yearOfRelease, int duration, String description, String country) {
        this.name = name;
        this.yearOfRelease = yearOfRelease;
        this.duration = duration;
        this.description = description;
        this.country = country;
    }
}