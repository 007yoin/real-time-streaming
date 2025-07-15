package com.live_stream.domain.camerasystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraSystemDto {

    private String name;
    private String description;
    private boolean isActive;
    private boolean isDeleted;

}
