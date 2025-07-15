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

    private final CameraService cs;
    private final CameraSearchService css;

    @PostMapping("/camera")
    public CameraDto save(@RequestBody CameraInsertDto cameraInsertDto) {
        log.debug("Save camera {}", cameraInsertDto);
        return cs.save(cameraInsertDto);
    }

    @GetMapping("/camera/recent")
    public Page<CameraDto> getRecentCameras(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("Get recent cameras {}", pageable);
        return css.getRecentCameras(pageable);
    }
}
