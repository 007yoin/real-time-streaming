package com.rts.cameraservice.domain.camera;

import com.rts.cameraservice.domain.camera.dto.CameraDto;
import com.rts.cameraservice.domain.camera.dto.CameraInsertDto;
import com.rts.cameraservice.domain.camera.dto.CameraSearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/camera/{id}")
    public CameraDto findCameraById(@PathVariable Long id) {
        return css.findCameraById(id);
    }

    @GetMapping("/camera/search")
    public Page<CameraDto> searchCameras(CameraSearchCondition condition,
                                         @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("Search cameras: {}", condition);
        return css.searchCameras(condition, pageable);
    }

    @PutMapping("/camera/{id}")
    public CameraDto update(@PathVariable Long id, @RequestBody CameraInsertDto cameraInsertDto) {
        log.debug("Update camera id {}: {}", id, cameraInsertDto);
        return cs.update(id, cameraInsertDto);
    }

    @PatchMapping("/cameras/activate")
    public void activeCameras(@RequestBody List<Long> cameraIds) {
        log.debug("Activate cameras {}", cameraIds);
        cs.activeCameras(cameraIds);
    }

    @PatchMapping("/cameras/deactivate")
    public void deactivateCameras(@RequestBody List<Long> cameraIds) {
        log.debug("Deactivate cameras {}", cameraIds);
        cs.deactivateCameras(cameraIds);
    }

    @DeleteMapping("/cameras")
    public void deleteCameras(@RequestBody List<Long> cameraIds) {
        log.debug("Delete cameras {}", cameraIds);
        cs.deleteCameras(cameraIds);
    }

}
