package dev.vorstu.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class SaveFilm {
    private Long id;
    private String name;
    private int yearOfRelease;
    private int duration;
    private String description;
    private String country;
    private Long genreId;
    private ArrayList<MultipartFile> images = new ArrayList<>();
    private ArrayList<MultipartFile> videos = new ArrayList<>();
}
