package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.dto.FilmDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @NotNull
    List<Genre> findAll();
    boolean existsByGenreNameAndIdNot(String nameOfTheGenre, Long id);
    boolean existsByGenreName(String nameOfTheGenre);
}
