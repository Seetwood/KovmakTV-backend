package dev.vorstu.controllers.films;

import dev.vorstu.db.repositories.GenreRepository;
import dev.vorstu.db.services.films.GenreService;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.mappers.GenreMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер, предназначенный для возаимодействия пользователей с жанрами
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/film/genres")
public class GenreController {
    private final  GenreRepository genreRepository;
    private final  GenreService genreService;

    @GetMapping()
    public List<GenreDto> getGenres() {
        return genreRepository.findAll().stream()
                .map(GenreMapper.MAPPER::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public GenreDto addGenre(@RequestBody GenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
        return genreService.updateGenre(id, genreDto);
    }
}