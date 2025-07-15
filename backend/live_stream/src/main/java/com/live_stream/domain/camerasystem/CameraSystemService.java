package com.live_stream.domain.camerasystem;

import com.live_stream.domain.camerasystem.dto.CameraSystemDto;
import com.live_stream.domain.camerasystem.dto.CameraSystemMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSystemService {

    private final CameraSystemRepository csr;
    private final CameraSystemMapper csm;

    @Transactional
    public CameraSystemDto save(CameraSystemDto cameraSystemDto) {
        CameraSystem newCameraSystem = CameraSystem.builder()
                .name(cameraSystemDto.getName())
                .description(cameraSystemDto.getDescription())
                .build();

        CameraSystem savedCameraSystem = csr.save(newCameraSystem);

        return csm.toDto(savedCameraSystem);
    }

}
