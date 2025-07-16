package com.live_stream.domain.camera.dto;

import com.live_stream.domain.camera.Camera;
import com.live_stream.domain.camera.CameraStatus;
import com.live_stream.domain.cameracategory.CameraCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private Long typeId;
    private String typeName;

    private Long largeCategoryId;
    private String largeCategoryName;
    private Long mediumCategoryId;
    private String mediumCategoryName;
    private Long smallCategoryId;
    private String smallCategoryName;

    public static CameraDto fromEntity(Camera camera) {
        CameraCategory cat = camera.getCameraCategory();
        CameraCategory large = null;
        CameraCategory medium = null;
        CameraCategory small = null;

        switch (cat.getCameraCategoryType()) {
            case LARGE -> large = cat;
            case MEDIUM -> {
                medium = cat;
                large = cat.getParent();
            }
            case SMALL -> {
                small = cat;
                medium = cat.getParent();
                large = medium.getParent();
            }
        }

        return CameraDto.builder()
                .id(camera.getId())
                .name(camera.getName())
                .description(camera.getDescription())
                .streamingUrl(camera.getStreamingUrl())
                .address(camera.getAddress())
                .latitude(camera.getLatitude())
                .longitude(camera.getLongitude())
                .status(camera.getStatus())
                .isActive(camera.isActive())
                .isDeleted(camera.isDeleted())

                .typeId(camera.getCameraType().getId())
                .typeName(camera.getCameraType().getName())

                .largeCategoryId(large != null ? large.getId() : null)
                .largeCategoryName(large != null ? large.getName() : null)

                .mediumCategoryId(medium != null ? medium.getId() : null)
                .mediumCategoryName(medium != null ? medium.getName() : null)

                .smallCategoryId(small != null ? small.getId() : null)
                .smallCategoryName(small != null ? small.getName() : null)
                .build();
    }
}