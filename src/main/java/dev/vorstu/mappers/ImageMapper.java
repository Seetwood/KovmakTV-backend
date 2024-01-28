package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ImageMapper {
    ImageMapper MAPPER = Mappers.getMapper( ImageMapper.class );

    ImageDto toDto(Image source);
}
