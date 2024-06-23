package dev.vorstu.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmDto {
    /** Идентификатор фильма */
    private Long id;

    /** Название  */
    private String name;

    /** Год релиза */
    private int yearOfRelease;

    /** Продолжительность */
    private int duration;

    /** Описание */
    private String description;

    /** Страна производства */
    private String country;

    /** Название жанра */
    private GenreDto genre;

    /** Список изображений */
    private List<ImageDto> imagesList;

    /** Список видео */
    private List<VideoDto> videosList;
}