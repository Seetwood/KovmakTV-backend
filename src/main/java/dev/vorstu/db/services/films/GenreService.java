package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.repositories.GenreRepository;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.exception.AlreadyExistsException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.GenreMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Сервис для взаимодействия с жанрами
 */
@Slf4j
@Service
@AllArgsConstructor
public class GenreService {
    private  final GenreRepository genreRepository;

    /**
     * Создание нового жанра
     * @param genreDto - Данные о новом жанре
     * @return Жанр
     */
    public GenreDto createGenre(GenreDto genreDto) {
        try {
            Genre genre = GenreMapper.MAPPER.toEntity(genreDto);
            Genre savedGenre = genreRepository.save(genre);
            return GenreMapper.MAPPER.toDto(savedGenre);
        }
        catch (ConstraintViolationException | DataIntegrityViolationException e) {
            log.warn("Жанр уже существует {}:", e.getMessage());
            throw new AlreadyExistsException("Жанр уже существует", e.getMessage());
        }
    }

    /**
     * Изменение названия жанра
     * @param genreId - Идентификатор жанра
     * @param genreDto - Новый жанр
     * @return Измененный жанр
     */
    public GenreDto updateGenre(Long genreId, GenreDto genreDto) {
        try {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Жанр уже существует"));
            genre.setGenreName(genreDto.getGenreName());
            Genre updatedGenre = genreRepository.save(genre);
            return GenreMapper.MAPPER.toDto(updatedGenre);
        }
        catch (ConstraintViolationException | DataIntegrityViolationException e) {
            log.warn("Жанр уже существует {}:", e.getMessage());
            throw new AlreadyExistsException("Жанр уже существует", e.getMessage());
        }
    }
}