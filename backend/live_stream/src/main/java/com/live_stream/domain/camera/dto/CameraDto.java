package com.live_stream.domain.camera.dto;

import com.live_stream.domain.camera.CameraStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraDto {
    private Long id;

    private String name;

    private String description;

    private String systemName;

    private String categoryLarge;

    private String categoryMedium;

    private String categorySmall;

    private String streamingUrl;

    private String cameraType;

    private String address;

    private Double latitude;

    private Double longitude;

    private CameraStatus status;

    private boolean isDeleted;
}
