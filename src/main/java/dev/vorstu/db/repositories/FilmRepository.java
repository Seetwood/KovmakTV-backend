package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.ShortFilmInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends PagingAndSortingRepository<Film, Long> {
    Page<Film> findAll(Specification<ShortFilmInfo> genresFilter, Pageable pageable);
    List<Film> findByGenreId(Long genreId);
}