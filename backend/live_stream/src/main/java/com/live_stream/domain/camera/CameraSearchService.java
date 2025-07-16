package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSearchService {

    private final CameraRepository cr;

    public Page<CameraDto> searchCameras(CameraSearchCondition condition, Pageable pageable) {
        return cr.search(condition, pageable);
    }

    public CameraDto findCameraById(Long id) {
        Camera camera = cr.findWithAllRelationsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camera not found with id: " + id));

        return CameraDto.fromEntity(camera);
    }
}
