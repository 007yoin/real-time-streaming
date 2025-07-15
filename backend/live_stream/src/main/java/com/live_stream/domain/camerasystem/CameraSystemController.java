package com.live_stream.domain.camerasystem;

import com.live_stream.domain.camerasystem.dto.CameraSystemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraSystemController {

    private final CameraSystemService css;

    @PostMapping("/camSys")
    public CameraSystemDto save(@RequestBody CameraSystemDto cameraSystemDto) {
        log.debug("Save camera system: {}", cameraSystemDto);
        return css.save(cameraSystemDto);
    }
}
