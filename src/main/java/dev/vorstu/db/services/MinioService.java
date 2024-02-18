package dev.vorstu.db.services;

import dev.vorstu.db.entities.films.Image;
import dev.vorstu.db.entities.films.Video;
import dev.vorstu.exception.CustomMinioException;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.policyJson}")
    private String pathPolicyJson;

    @Autowired
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void createBucket(String bucketName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                String policyJson = new String(Files.readAllBytes(Paths.get(pathPolicyJson)));
                policyJson = policyJson.replace("bucketName", bucketName);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policyJson).build());
                log.info("bucket successfully created.");
            }
        }
        catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error create bucket: {}", e.getMessage());
            throw new CustomMinioException("Error when creating a bucket: " + e.getMessage());
        }
    }

    public void uploadFile(ArrayList<MultipartFile> files, String bucketName) {
        files.forEach(file -> {
            try {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
            }
            catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                log.error("Error when uploading a file: {}", e.getMessage());
                throw new CustomMinioException("Error when uploading a file: " + e.getMessage());
            }
        });
    }

    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        }
        catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error when deleting a bucket: {}", e.getMessage());
            throw new CustomMinioException("Error when deleting a bucket: " + e.getMessage());
        }
    }

    public void removeImages(List<Image> images, String bucketName) {
        try {
            List<DeleteObject> objects = images.stream()
                    .map(image -> new DeleteObject(image.getImageName()))
                    .collect(Collectors.toList());
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                            RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("Error in deleting object {}; {}", error.objectName(), error.message());
            }
        }
        catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error deleting images: {}", e.getMessage());
            throw new CustomMinioException("Error deleting images: " + e.getMessage());
        }
    }

    public void removeVideos(List<Video> videos, String bucketName) {
        try {
            List<DeleteObject> objects = videos.stream()
                    .map(image -> new DeleteObject(image.getTraillerName()))
                    .collect(Collectors.toList());
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                            RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("Error in deleting object {}; {}", error.objectName(), error.message());
            }
        }
        catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error deleting videos: {}", e.getMessage());
            throw new CustomMinioException("Error deleting videos: " + e.getMessage());
        }
    }
}