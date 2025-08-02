package com.rts.cameraservice.domain.cameracategory.dto;

import com.rts.cameraservice.domain.cameracategory.CameraCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CameraCategoryMapper {

    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "parent.id", target = "parentId")
    CameraCategoryDto toDto(CameraCategory cameraCategory);

}
