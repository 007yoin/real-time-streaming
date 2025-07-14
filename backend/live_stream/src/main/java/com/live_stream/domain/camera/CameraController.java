package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraController {

    private final CameraService cameraService;

    @PostMapping("/camera")
    public CameraInsertDto saveCamera(@RequestBody CameraInsertDto cameraInsertDto) {
        cameraService.saveCamera(cameraInsertDto);

        log.info("Save camera {}", cameraInsertDto);

        return cameraInsertDto;
    }
}
