package com.rts.cameraservice.domain.cameratype.dto;

import com.rts.cameraservice.domain.cameratype.CameraType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CameraTypeMapper {

    CameraTypeDto toDto(CameraType cameraType);

}
