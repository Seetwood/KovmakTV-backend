package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FilmMapper {
    FilmMapper MAPPER = Mappers.getMapper(FilmMapper.class);
    ShortFilmInfo toShortFilmDto(Film source);
    FilmDto toFilmDto(Film source);
    ImageDto toImageDto(Image image);

    @Mapping(target = "videosList",ignore = true)
    @Mapping(target = "reviewList",ignore = true)
    @Mapping(target = "imagesList",ignore = true)
    @Mapping(target = "genre",ignore = true)
    Film toEntity(SaveFilm newFilmSave);

    @Mapping(target = "videosList",ignore = true)
    @Mapping(target = "reviewList",ignore = true)
    @Mapping(target = "imagesList",ignore = true)
    @Mapping(target = "genre",ignore = true)
    void updateFilm(SaveFilm saveFilm, @MappingTarget Film film);
}