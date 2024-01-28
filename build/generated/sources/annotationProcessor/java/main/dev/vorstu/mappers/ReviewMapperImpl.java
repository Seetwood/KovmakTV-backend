package dev.vorstu.mappers;

import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.dto.ReviewDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-25T13:08:54+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDto toDto(Review source) {
        if ( source == null ) {
            return null;
        }

        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId( source.getId() );
        reviewDto.setFilmId( source.getFilmId() );
        reviewDto.setUserId( source.getUserId() );
        reviewDto.setHeader( source.getHeader() );
        reviewDto.setTextReview( source.getTextReview() );
        reviewDto.setStatus( source.getStatus() );

        return reviewDto;
    }

    @Override
    public Review toEntity(ReviewDto ReviewDto) {
        if ( ReviewDto == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( ReviewDto.getId() );
        review.setFilmId( ReviewDto.getFilmId() );
        review.setUserId( ReviewDto.getUserId() );
        review.setTextReview( ReviewDto.getTextReview() );
        review.setHeader( ReviewDto.getHeader() );
        review.setStatus( ReviewDto.getStatus() );

        return review;
    }
}
