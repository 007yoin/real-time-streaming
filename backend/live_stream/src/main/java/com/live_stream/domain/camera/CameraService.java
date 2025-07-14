package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;

    public void saveCamera(CameraInsertDto cameraInsertDto) {
        Camera camera = Camera.builder()
                .name(cameraInsertDto.getName())
                .description(cameraInsertDto.getDescription())
                .systemName(cameraInsertDto.getSystemName())
                .categoryLarge(cameraInsertDto.getCategoryLarge())
                .categoryMedium(cameraInsertDto.getCategoryMedium())
                .categorySmall(cameraInsertDto.getCategorySmall())
                .streamingUrl(cameraInsertDto.getStreamingUrl())
                .cameraType(cameraInsertDto.getCameraType())
                .address(cameraInsertDto.getAddress())
                .latitude(cameraInsertDto.getLatitude())
                .longitude(cameraInsertDto.getLongitude())
                .build();

        cameraRepository.save(camera);
    }
}
