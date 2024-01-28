package dev.vorstu.dto;

import lombok.*;

import java.util.List;

@Data
public class ShortFilmInfo {
    private Long id;
    private String name;
    private int yearOfRelease;
    private int duration;
    private String description;
    private String country;
    private GenreDto genre;
    private List<ImageDto> imagesList;
}