package dev.vorstu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    /** Название трейлера*/
    private String traillerName;

    /** Ссылка (URL) на трейлер */
    private String traillerUrl;
}