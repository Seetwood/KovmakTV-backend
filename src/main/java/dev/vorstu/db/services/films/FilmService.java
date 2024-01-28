package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.db.entities.films.Video;
import dev.vorstu.db.repositories.*;
import dev.vorstu.db.services.MinioService;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.ImageDto;
import dev.vorstu.dto.SaveFilm;
import dev.vorstu.dto.ShortFilmInfo;
import dev.vorstu.mappers.FilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Transactional
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MinioService minioService;
    @Autowired
    GenreService genreService;

    public Film findById(Long filmId) {
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new IllegalArgumentException("Фильм не найден"));
    }

    @Transactional
    public Page<ShortFilmInfo> getShortFilmInfo(String genre, Pageable pageable) {
        Specification<ShortFilmInfo> genreFilter = (root, query, criteriaBuilder) -> {
            if (genre != null && !genre.isEmpty()) {
                Join<Film, Genre> genreJoin = root.join("genre");
                return criteriaBuilder.equal(genreJoin.get("genreName"), genre);
            }
            return null;
        };
        return filmRepository.findAll(genreFilter, pageable).map(film -> {
            ShortFilmInfo shortFilmInfo = FilmMapper.MAPPER.toShortFilmDto(film);
            if (film.getImagesList() != null && !film.getImagesList().isEmpty()) {
                ImageDto imageDto = FilmMapper.MAPPER.toImageDto(film.getImagesList().get(0));
                shortFilmInfo.setImagesList(Collections.singletonList(imageDto));
            }
            return shortFilmInfo;
        });
    }


    @Transactional
    public FilmDto createFilm(SaveFilm newFilmSave) {
        Film film = FilmMapper.MAPPER.toEntity(newFilmSave);
        Film newFilm = new Film(
                film.getName(),
                film.getYearOfRelease(),
                film.getDuration(),
                film.getDescription(),
                film.getCountry(),
                newFilmSave.getGenreId()
        );
        Genre genre = genreRepository.findById(newFilmSave.getGenreId()).orElse(null);
        newFilm.setGenre(genre);
        newFilm = filmRepository.save(newFilm);
        String bucketName = newFilm.getId().toString() + "-filmbucket";
        minioService.createBucket(bucketName);

        saveImages(newFilmSave.getImages(), bucketName, newFilm);
        saveVideos(newFilmSave.getVideos(), bucketName, newFilm);
        return FilmMapper.MAPPER.toFilmDto(newFilm);
    }

    public void saveImages(List<MultipartFile> images, String bucketName, Film film) {
        if (!images.isEmpty()) {
            minioService.uploadFile((ArrayList<MultipartFile>) images, bucketName);
            List<Image> imagesList = new ArrayList<>();
            images.forEach(file -> {
                Image image = new Image(film.getId(), file.getOriginalFilename(), minioService.getFileUrl(file.getOriginalFilename(), bucketName));
                imagesList.add(image);
            });
            film.setImagesList(imagesList);
            imageRepository.saveAll(imagesList);
        }
    }

    public void saveVideos(List<MultipartFile> videos, String bucketName, Film film) {
        if (!videos.isEmpty()) {
            minioService.uploadFile((ArrayList<MultipartFile>) videos, bucketName);
            List<Video> videosList = new ArrayList<>();
            videos.forEach(file -> {
                Video video = new Video(film.getId(), file.getOriginalFilename(), minioService.getFileUrl(file.getOriginalFilename(), bucketName));
                videosList.add(video);
            });
            film.setVideosList(videosList);
            videoRepository.saveAll(videosList);
        }
    }

    @Transactional
    public FilmDto updateFilm(Long filmId, SaveFilm updatedFilm) {
        Film oldFilm = filmRepository.findById(filmId).orElseThrow(() -> new IllegalArgumentException("Фильм не найден"));

        oldFilm.setName(updatedFilm.getName());
        oldFilm.setYearOfRelease(updatedFilm.getYearOfRelease());
        oldFilm.setDuration(updatedFilm.getDuration());
        oldFilm.setDescription(updatedFilm.getDescription());
        oldFilm.setCountry(updatedFilm.getCountry());
        oldFilm.setGenreId(updatedFilm.getGenreId());

        oldFilm = filmRepository.save(oldFilm);
        return FilmMapper.MAPPER.toFilmDto(oldFilm);
    }

    @Transactional
    public void deleteFilm(Long filmId) {
        Film film = findById(filmId);
        String bucketName = film.getId().toString() + "-filmbucket";
        if (!film.getImagesList().isEmpty()) {
            minioService.removeImages(film.getImagesList(), bucketName);
        }
        if (!film.getVideosList().isEmpty()) {
            minioService.removeVideos(film.getVideosList(), bucketName);
        }
        minioService.removeBucket(bucketName);
        filmRepository.deleteById(filmId);
        imageRepository.deleteByFilmId(filmId);
        videoRepository.deleteByFilmId(filmId);
    }
}