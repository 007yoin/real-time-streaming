package com.rts.cameraservice.domain.camera;

import com.rts.cameraservice.domain.camera.dto.CameraDto;
import com.rts.cameraservice.domain.camera.dto.CameraSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSearchService {

    private final CameraRepository cr;

    public Camera findById(Long id) {
        return cr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camera not found with id: " + id));
    }

    public CameraDto findCameraById(Long id) {
        Camera camera = cr.findWithAllRelationsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camera not found with id: " + id));

        return CameraDto.fromEntity(camera);
    }

    public Page<CameraDto> searchCameras(CameraSearchCondition condition, Pageable pageable) {
        return cr.search(condition, pageable);
    }
}
