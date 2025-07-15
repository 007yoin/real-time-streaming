package com.live_stream.domain.cameratype.dto;

import com.live_stream.domain.cameratype.CameraType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CameraTypeMapper {

    CameraTypeDto toDto(CameraType cameraType);

}
