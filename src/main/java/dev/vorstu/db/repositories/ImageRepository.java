package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByFilmId(Long FilmId);
}