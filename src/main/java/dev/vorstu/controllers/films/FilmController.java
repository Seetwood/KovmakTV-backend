package dev.vorstu.controllers.films;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.services.films.FilmService;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.SaveFilm;
import dev.vorstu.dto.ShortFilmInfo;
import dev.vorstu.mappers.FilmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/film/")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("shortFilmsInfo")
    public Page<ShortFilmInfo> getShortFilmInfo(@RequestParam(defaultValue = "0") int pageIndex,
                                                @RequestParam(defaultValue = "15") int pageSize,
                                                @RequestParam(required = false, defaultValue = "") String filter) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return filmService.getShortFilmInfo(filter, pageable);
    }

    @GetMapping("id={id}")
    @Transactional
    public FilmDto getPageFilm(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return FilmMapper.MAPPER.toFilmDto(film);
    }

    @PostMapping()
    public FilmDto addFilm(@ModelAttribute SaveFilm saveFilm) {
        return filmService.createFilm(saveFilm);
    }

    @PutMapping("{id}")
        public FilmDto updateFilm(@PathVariable Long id, @ModelAttribute SaveFilm updateFilm) {
        return filmService.updateFilm(id, updateFilm);
    }

    @DeleteMapping("{id}")
    public Long deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return id;
    }
}