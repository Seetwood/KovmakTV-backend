package dev.vorstu.controllers.films;

import dev.vorstu.db.repositories.GenreRepository;
import dev.vorstu.db.services.films.GenreService;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/film/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreService genreService;

    @GetMapping()
    public List<GenreDto> getGenres() {

        return genreRepository.findAll().stream()
                .map(GenreMapper.MAPPER::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public GenreDto addGenre(@RequestBody GenreDto genreDto) {
        return genreService.addGenre(genreDto);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
        return genreService.updateGenre(id, genreDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteGenre(@PathVariable Long id) {
        return genreService.DeleteGenre(id);
    }
}