package dev.vorstu.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * Дто для создания нового фильма
 */
@Data
public class SaveFilm {
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

    /** Идентификатор жанра */
    private Long genreId;

    /** Список файлов изображений */
    private ArrayList<MultipartFile> images = new ArrayList<>();

    /** Список файлов видео */
    private ArrayList<MultipartFile> videos = new ArrayList<>();
}
