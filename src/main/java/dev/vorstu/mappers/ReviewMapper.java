package dev.vorstu.mappers;


import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.dto.ReviewDto;
import dev.vorstu.dto.SaveFilm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper( ReviewMapper.class );

    ReviewDto toDto(Review source);

    Review toEntity(ReviewDto ReviewDto);
}