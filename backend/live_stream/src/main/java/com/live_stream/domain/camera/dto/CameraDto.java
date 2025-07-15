package com.live_stream.domain.camera.dto;

import com.live_stream.domain.camera.CameraStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CameraDto {
    private Long id;
    private String name;
    private String description;
    private String streamingUrl;
    private String address;
    private Double latitude;
    private Double longitude;
    private CameraStatus status;
    private boolean isActive;
    private boolean isDeleted;

    private Long systemId;
    private String systemName;
    private Long typeId;
    private String typeName;

    private Long largeCategoryId;
    private String largeCategoryName;
    private Long mediumCategoryId;
    private String mediumCategoryName;
    private Long smallCategoryId;
    private String smallCategoryName;
}
