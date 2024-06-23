package dev.vorstu.dto;

import lombok.Data;

@Data
public class VideoDto {
    /** Название трейлера*/
    private String traillerName;

    /** Ссылка (URL) на трейлер*/
    private String traillerUrl;
}