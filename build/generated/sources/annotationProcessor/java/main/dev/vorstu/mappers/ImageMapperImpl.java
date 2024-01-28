package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Image;
import dev.vorstu.dto.ImageDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-22T12:41:49+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDto toDto(Image source) {
        if ( source == null ) {
            return null;
        }

        ImageDto imageDto = new ImageDto();

        imageDto.setImageName( source.getImageName() );
        imageDto.setImageUrl( source.getImageUrl() );

        return imageDto;
    }
}
