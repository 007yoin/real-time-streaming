package com.live_stream.domain.camera.dto;

import com.live_stream.domain.camera.Camera;
import com.live_stream.domain.cameracategory.CameraCategory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CameraMapper {

    @Mapping(source = "camera.cameraType.id", target = "typeId")
    @Mapping(source = "camera.cameraType.name", target = "typeName")
    CameraDto toDto(Camera camera);

    @AfterMapping
    default void fillCategoryHierarchy(Camera camera, @MappingTarget CameraDto dto) {
        CameraCategory cat = camera.getCameraCategory();
        if (cat == null || cat.getCameraCategoryType() == null) {
            return;
        }

        switch (cat.getCameraCategoryType()) {
            case SMALL -> {
                dto.setSmallCategoryId(cat.getId());
                dto.setSmallCategoryName(cat.getName());
                CameraCategory mid = cat.getParent();
                if (mid != null) {
                    dto.setMediumCategoryId(mid.getId());
                    dto.setMediumCategoryName(mid.getName());
                    CameraCategory large = mid.getParent();
                    if (large != null) {
                        dto.setLargeCategoryId(large.getId());
                        dto.setLargeCategoryName(large.getName());
                    }
                }
            }
            case MEDIUM -> {
                dto.setMediumCategoryId(cat.getId());
                dto.setMediumCategoryName(cat.getName());
                CameraCategory large = cat.getParent();
                if (large != null) {
                    dto.setLargeCategoryId(large.getId());
                    dto.setLargeCategoryName(large.getName());
                }
            }
            case LARGE -> {
                dto.setLargeCategoryId(cat.getId());
                dto.setLargeCategoryName(cat.getName());
            }
        }
    }
}
