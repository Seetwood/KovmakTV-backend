package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.repositories.GenreRepository;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.exception.AlreadyExistsException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.GenreMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public GenreDto createGenre(GenreDto genreDto) {
        try {
            Genre genre = GenreMapper.MAPPER.toEntity(genreDto);
            Genre savedGenre = genreRepository.save(genre);
            return GenreMapper.MAPPER.toDto(savedGenre);
        }
        catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new AlreadyExistsException("genre already exists");
        }
    }

    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        try {
            Genre genre = genreRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("genre is not found"));
            genre.setGenreName(genreDto.getGenreName());
            Genre updatedGenre = genreRepository.save(genre);
            return GenreMapper.MAPPER.toDto(updatedGenre);
        }
        catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new AlreadyExistsException("genre already exists");
        }
    }
}