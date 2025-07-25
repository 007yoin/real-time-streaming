package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CameraRepositoryCustom {
    Page<CameraDto> search(CameraSearchCondition condition, Pageable pageable);
}
