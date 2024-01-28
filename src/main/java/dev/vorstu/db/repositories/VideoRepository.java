package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.films.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    void deleteByFilmId(Long filmId);
}