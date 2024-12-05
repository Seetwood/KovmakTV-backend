package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.films.Image;
import dev.vorstu.db.entities.films.Video;
import dev.vorstu.db.repositories.*;
import dev.vorstu.db.services.MinioService;
import dev.vorstu.dto.FilmDto;
import dev.vorstu.dto.ImageDto;
import dev.vorstu.dto.SaveFilmDto;
import dev.vorstu.dto.ShortFilmInfoDto;
import dev.vorstu.exception.BusinessException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.FilmMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сервис для взаимодействия с фильмами
 */
@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final ImageRepository imageRepository;
    private final  VideoRepository videoRepository;
    private final MinioService minioService;

    /**
     * Получение фильма по его идентификатору
     * @param filmId - Идентификатор фильма
     * @return Найденный фильм
     */
    public Film findById(Long filmId) {
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    /**
     * Получение страницы с фильмом
     * @param filmId - Идентификатор фильма
     * @return Страница с фильмом
     */
    public FilmDto getPageFilm(Long filmId) {
        Film film = this.findById(filmId);
        return FilmMapper.MAPPER.toFilmDto(film);
    }

    /**
     * Получение карточек с фильмами с фильтрацией и без фильтрации по жанру
     * @param genreName - Название жанра
     * @param pageable -
     * @return Страница с карточками фильмов
     */
    @Transactional
    public Page<ShortFilmInfoDto> getShortFilmInfo(String genreName, Pageable pageable) {
        Specification<ShortFilmInfoDto> genreFilter = (root, query, criteriaBuilder) -> {
            if (genreName != null && !genreName.isEmpty()) {
                Join<Film, Genre> genreJoin = root.join("genre");
                return criteriaBuilder.equal(genreJoin.get("genreName"), genreName);
            }
            return null;
        };
        return filmRepository.findAll(genreFilter, pageable).map(film -> {

            ShortFilmInfoDto shortFilmInfoDto = FilmMapper.MAPPER.toShortFilmDto(film);
            if (film.getImagesList() != null && !film.getImagesList().isEmpty()) {
                ImageDto imageDto = FilmMapper.MAPPER.toImageDto(film.getImagesList().get(0));
                shortFilmInfoDto.setImagesList(Collections.singletonList(imageDto));
            }
            return shortFilmInfoDto;
        });
    }

    /**
     * Создание нового фильма
     * @param newFilm - Данные о новом фильме
     * @return Фильм
     */
    @Transactional
    public FilmDto createFilm(SaveFilmDto newFilm) {
        Film film = FilmMapper.MAPPER.toEntity(newFilm);
        film = filmRepository.save(film);

        String bucketName = film.getId().toString() + "-filmbucket";
        minioService.createBucket(bucketName);
        saveImages(newFilm.getImages(), bucketName, film);
        saveVideos(newFilm.getVideos(), bucketName, film);
        return FilmMapper.MAPPER.toFilmDto(film);
    }

    /**
     * Сохранение изображений к фильму
     * @param images - Список файлов-изображений
     * @param bucketName - Название бакета для сохранения
     * @param film - Фильм
     */
    public void saveImages(List<MultipartFile> images, String bucketName, Film film) {
        if (!images.isEmpty()) {
            minioService.uploadFile((ArrayList<MultipartFile>) images, bucketName);
            List<Image> imagesList = new ArrayList<>();
            images.forEach(file -> {
                String imageUrl = bucketName +"/" + file.getOriginalFilename();
                Image image = Image.builder()
                        .film(film)
                        .imageName(file.getOriginalFilename())
                        .imageUrl(imageUrl)
                        .build();
                imagesList.add(image);
            });
            film.setImagesList(imagesList);
            imageRepository.saveAll(imagesList);
        }
    }

    /**
     * Сохранение видео к фильму
     * @param videos - Список файлов-видео
     * @param bucketName - Название бакета для сохранения
     * @param film - Фильм
     */
    public void saveVideos(List<MultipartFile> videos, String bucketName, Film film) {
        if (!videos.isEmpty()) {
            minioService.uploadFile((ArrayList<MultipartFile>) videos, bucketName);
            List<Video> videosList = new ArrayList<>();
            videos.forEach(file -> {
                String videoUrl = bucketName +"/" + file.getOriginalFilename();
                Video video = Video.builder()
                        .film(film)
                        .traillerName(file.getOriginalFilename())
                        .traillerUrl(videoUrl)
                        .build();
                videosList.add(video);
            });
            film.setVideosList(videosList);
            videoRepository.saveAll(videosList);
        }
    }

    /**
     * Обновление данных о фильме
     * @param filmId - Идентификатор фильма
     * @param updatedFilm - Новые данные
     * @return Обновленный фильм
     */
    @Transactional
    public FilmDto updateFilm(Long filmId, SaveFilmDto updatedFilm) {
        try {
            Film film = findById(filmId);
            FilmMapper.MAPPER.updateFilm(updatedFilm, film);
            filmRepository.save(film);
            return FilmMapper.MAPPER.toFilmDto(film);
        }
        catch (BusinessException e) {
            log.error("Ошибка при обновлении данных фильма: {}", e.getMessage());
            throw new BusinessException("Ошибка при обновлении данных фильма: {}", e.getMessage());
        }

    }

    /**
     * Удаление фильма
     * @param filmId - Идентификатор фильма
     */
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
    }
}