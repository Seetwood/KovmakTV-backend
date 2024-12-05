package dev.vorstu.db.entities.films;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название */
    @NonNull
    @Column(unique=true)
    private String genreName;
}