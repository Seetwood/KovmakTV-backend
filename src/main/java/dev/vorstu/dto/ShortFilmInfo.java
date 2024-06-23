package dev.vorstu.dto;

import lombok.*;

import java.util.List;

/**
 * Информация о фильме для карточки
 */
@Data
public class ShortFilmInfo {
    /** Идентификатор фильма */
    private Long id;

    /** Название */
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
}