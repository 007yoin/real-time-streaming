package com.live_stream.domain.camera.dto;

import com.live_stream.domain.camera.CameraStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraSearchCondition {
    private String name;
    private Boolean active;
    private CameraStatus status;

    private Long categoryId;
}