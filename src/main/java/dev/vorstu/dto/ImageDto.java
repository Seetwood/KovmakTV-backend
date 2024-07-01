package dev.vorstu.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    /** Название изображения*/
    private String imageName;

    /** Ссылка (URL) на изображение*/
    private String imageUrl;
}
