package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSearchService {

    private final CameraRepository cameraRepository;

    public Page<CameraDto> getRecentCameras(Pageable pageable) {
        return cameraRepository.findRecentCameraDtos(pageable);
    }
}
