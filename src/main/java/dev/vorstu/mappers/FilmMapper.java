package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FilmMapper {
    FilmMapper MAPPER = Mappers.getMapper(FilmMapper.class);
    ShortFilmInfo toShortFilmDto(Film source);
    FilmDto toFilmDto(Film source);
    ImageDto toImageDto(Image image);
    Film toEntity(SaveFilm newFilmSave);
}