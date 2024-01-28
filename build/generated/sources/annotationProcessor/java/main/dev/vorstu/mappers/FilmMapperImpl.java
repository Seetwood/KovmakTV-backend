package dev.vorstu.mappers;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.db.entities.films.Video;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.dto.ImageDto;
import dev.vorstu.dto.SaveFilm;
import dev.vorstu.dto.ShortFilmInfo;
import dev.vorstu.dto.VideoDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-26T16:01:15+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class FilmMapperImpl implements FilmMapper {

    @Override
    public ShortFilmInfo toShortFilmDto(Film source) {
        if ( source == null ) {
            return null;
        }

        ShortFilmInfo shortFilmInfo = new ShortFilmInfo();

        shortFilmInfo.setId( source.getId() );
        shortFilmInfo.setName( source.getName() );
        shortFilmInfo.setYearOfRelease( source.getYearOfRelease() );
        shortFilmInfo.setDuration( source.getDuration() );
        shortFilmInfo.setDescription( source.getDescription() );
        shortFilmInfo.setCountry( source.getCountry() );
        shortFilmInfo.setGenre( genreToGenreDto( source.getGenre() ) );
        shortFilmInfo.setImagesList( imageListToImageDtoList( source.getImagesList() ) );

        return shortFilmInfo;
    }

    @Override
    public FilmDto toFilmDto(Film source) {
        if ( source == null ) {
            return null;
        }

        FilmDto filmDto = new FilmDto();

        filmDto.setId( source.getId() );
        filmDto.setName( source.getName() );
        filmDto.setYearOfRelease( source.getYearOfRelease() );
        filmDto.setDuration( source.getDuration() );
        filmDto.setDescription( source.getDescription() );
        filmDto.setCountry( source.getCountry() );
        filmDto.setGenre( genreToGenreDto( source.getGenre() ) );
        filmDto.setImagesList( imageListToImageDtoList( source.getImagesList() ) );
        filmDto.setVideosList( videoListToVideoDtoList( source.getVideosList() ) );

        return filmDto;
    }

    @Override
    public ImageDto toImageDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDto imageDto = new ImageDto();

        imageDto.setImageName( image.getImageName() );
        imageDto.setImageUrl( image.getImageUrl() );

        return imageDto;
    }

    @Override
    public Film toEntity(SaveFilm newFilmSave) {
        if ( newFilmSave == null ) {
            return null;
        }

        Film film = new Film();

        film.setId( newFilmSave.getId() );
        film.setName( newFilmSave.getName() );
        film.setYearOfRelease( newFilmSave.getYearOfRelease() );
        film.setDuration( newFilmSave.getDuration() );
        film.setDescription( newFilmSave.getDescription() );
        film.setCountry( newFilmSave.getCountry() );
        film.setGenreId( newFilmSave.getGenreId() );

        return film;
    }

    protected GenreDto genreToGenreDto(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDto genreDto = new GenreDto();

        genreDto.setId( genre.getId() );
        genreDto.setGenreName( genre.getGenreName() );

        return genreDto;
    }

    protected List<ImageDto> imageListToImageDtoList(List<Image> list) {
        if ( list == null ) {
            return null;
        }

        List<ImageDto> list1 = new ArrayList<ImageDto>( list.size() );
        for ( Image image : list ) {
            list1.add( toImageDto( image ) );
        }

        return list1;
    }

    protected VideoDto videoToVideoDto(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoDto videoDto = new VideoDto();

        videoDto.setTraillerName( video.getTraillerName() );
        videoDto.setTraillerUrl( video.getTraillerUrl() );

        return videoDto;
    }

    protected List<VideoDto> videoListToVideoDtoList(List<Video> list) {
        if ( list == null ) {
            return null;
        }

        List<VideoDto> list1 = new ArrayList<VideoDto>( list.size() );
        for ( Video video : list ) {
            list1.add( videoToVideoDto( video ) );
        }

        return list1;
    }
}
