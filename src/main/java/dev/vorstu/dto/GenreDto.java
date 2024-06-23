package dev.vorstu.dto;

import lombok.*;

@Data
public class GenreDto {
    /** Идентификатор жанра фильма */
    private Long id;

    /** Название жанра фильма */
    private String genreName;
}
