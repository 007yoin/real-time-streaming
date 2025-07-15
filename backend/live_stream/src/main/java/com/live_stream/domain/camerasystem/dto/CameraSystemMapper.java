package com.live_stream.domain.camerasystem.dto;

import com.live_stream.domain.camerasystem.CameraSystem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CameraSystemMapper {

    CameraSystemDto toDto(CameraSystem cameraSystem);

}
