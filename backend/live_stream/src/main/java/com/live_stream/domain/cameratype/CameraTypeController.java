package com.live_stream.domain.cameratype;

import com.live_stream.domain.cameratype.dto.CameraTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraTypeController {

    private final CameraTypeService cts;
    private final CameraTypeSearchService ctss;

    @PostMapping("camType")
    public CameraTypeDto save(@RequestBody CameraTypeDto cameraTypeDto) {
        log.debug("Save camera type: {}", cameraTypeDto);
        return cts.save(cameraTypeDto);
    }

    @GetMapping("/camType")
    public List<CameraTypeDto> getTypes() {
        log.debug("Get types");
        return ctss.getTypes();
    }
}
