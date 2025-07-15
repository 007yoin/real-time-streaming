package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSearchService {

    private final CameraRepository cr;
    private final CameraMapper cm;

    public Page<CameraDto> getRecentCameras(Pageable pageable) {
        Page<Camera> cameras = cr.findByIsDeletedFalse(pageable);
        return cameras.map(cm::toDto);
    }
}
