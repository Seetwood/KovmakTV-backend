package dev.vorstu.mappers;

import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper( ReviewMapper.class );

    @Mapping(target = "surname", source = "user.surname")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "filmName", source = "film.name")
    @Mapping(target = "comments",ignore = true)
    ReviewDto toDto(Review source);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "rating",ignore = true)
    @Mapping(target = "film",ignore = true)
    @Mapping(target = "commentList",ignore = true)
    Review toEntity(ReviewDto reviewDto);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "rating",ignore = true)
    @Mapping(target = "film",ignore = true)
    @Mapping(target = "commentList",ignore = true)
    void updateReview(ReviewDto reviewDto, @MappingTarget Review review);


}