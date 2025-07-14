package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import com.live_stream.domain.camera.dto.CameraInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraController {

    private final CameraService cameraService;
    private final CameraSearchService cameraSearchService;

    @PostMapping("/camera")
    public CameraInsertDto saveCamera(@RequestBody CameraInsertDto cameraInsertDto) {
        cameraService.saveCamera(cameraInsertDto);

        log.info("Save camera {}", cameraInsertDto);

        return cameraInsertDto;
    }

    @GetMapping("/camera/recent")
    public Page<CameraDto> getRecentCameras(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return cameraSearchService.getRecentCameras(pageable);
    }
}
