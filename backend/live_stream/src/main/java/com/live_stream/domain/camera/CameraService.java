package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraInsertDto;
import com.live_stream.domain.camera.dto.CameraMapper;
import com.live_stream.domain.cameracategory.CameraCategory;
import com.live_stream.domain.cameracategory.CameraCategorySearchService;
import com.live_stream.domain.camerasystem.CameraSystem;
import com.live_stream.domain.camerasystem.CameraSystemSearchService;
import com.live_stream.domain.cameratype.CameraType;
import com.live_stream.domain.cameratype.CameraTypeSearchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cr;
    private final CameraMapper cm;

    private final CameraCategorySearchService ccss;
    private final CameraSystemSearchService csss;
    private final CameraTypeSearchService ctss;

    @Transactional
    public CameraDto save(CameraInsertDto dto) {
        CameraCategory cat = ccss.findById(dto.getCameraCategoryId());
        CameraSystem sys = csss.findById(dto.getSystemId());
        CameraType type = ctss.findById(dto.getCameraTypeId());

        Camera cam = Camera.builder()
                .cameraCategory(cat)
                .cameraSystem(sys)
                .cameraType(type)
                .name(dto.getName())
                .description(dto.getDescription())
                .streamingUrl(dto.getStreamingUrl())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        Camera saved = cr.save(cam);

        return cm.toDto(saved);
    }
}
