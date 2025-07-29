package com.rts.cameraservice.domain.camera;

import com.rts.cameraservice.domain.camera.dto.CameraDto;
import com.rts.cameraservice.domain.camera.dto.CameraSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CameraRepositoryCustom {
    Page<CameraDto> search(CameraSearchCondition condition, Pageable pageable);
}
