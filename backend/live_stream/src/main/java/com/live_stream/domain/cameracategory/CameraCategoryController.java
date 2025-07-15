package com.live_stream.domain.cameracategory;

import com.live_stream.domain.cameracategory.dto.CameraCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraCategoryController {

    private final CameraCategoryService ccs;

    @PostMapping("camCategory")
    public CameraCategoryDto save(@RequestBody CameraCategoryDto cameraCategoryDto) {
        log.debug("Save camera category: {}", cameraCategoryDto);
        return ccs.save(cameraCategoryDto);
    }
}
