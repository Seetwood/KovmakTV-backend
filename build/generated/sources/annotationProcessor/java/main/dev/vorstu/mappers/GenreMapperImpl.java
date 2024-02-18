package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.dto.GenreDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-13T14:56:15+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDto toDto(Genre source) {
        if ( source == null ) {
            return null;
        }

        GenreDto genreDto = new GenreDto();

        genreDto.setId( source.getId() );
        genreDto.setGenreName( source.getGenreName() );

        return genreDto;
    }

    @Override
    public Genre toEntity(GenreDto genreDto) {
        if ( genreDto == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setId( genreDto.getId() );
        genre.setGenreName( genreDto.getGenreName() );

        return genre;
    }
}
