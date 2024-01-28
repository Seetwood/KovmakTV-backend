package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreMapper {
    GenreMapper MAPPER = Mappers.getMapper( GenreMapper.class );
    GenreDto toDto(Genre source);
    Genre toEntity(GenreDto genreDto);
}

