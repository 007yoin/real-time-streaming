package com.rts.cameraservice.domain.cameratype;

import com.rts.cameraservice.domain.cameratype.dto.CameraTypeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraTypeSearchService {

    private final CameraTypeRepository ctr;

    public CameraType findById(Long id) {
        return ctr.findById(id).orElseThrow(
                () -> new IllegalArgumentException("CameraType not found with id: " + id)
        );
    }

    public List<CameraTypeDto> getTypes() {
        return ctr.findAllAsDto();
    }
}
