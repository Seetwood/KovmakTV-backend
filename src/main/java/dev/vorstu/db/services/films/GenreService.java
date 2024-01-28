package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.repositories.FilmRepository;
import dev.vorstu.db.repositories.GenreRepository;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private FilmRepository filmRepository;

    public GenreDto addGenre(GenreDto genreDto) {
        if (genreRepository.existsByGenreName(genreDto.getGenreName())) {
            throw new IllegalArgumentException("Жанр с таким названием уже существует");
        }
        Genre genre = GenreMapper.MAPPER.toEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.MAPPER.toDto(savedGenre);
    }

    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        if (genreRepository.existsByGenreNameAndIdNot(genreDto.getGenreName(), id)) {
            throw new IllegalArgumentException("Жанр с таким названием уже существует");
        }
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Жанра с таким id не существует"));
        genre.setGenreName(genreDto.getGenreName());
        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapper.MAPPER.toDto(updatedGenre);
    }

    public Long DeleteGenre(Long genreId) {
        List<Film> filmsList = filmRepository.findByGenreId(genreId);
        for (Film film : filmsList) {
            film.setGenreId(null);
        }
        genreRepository.deleteById(genreId);
        return genreId;
    }
}