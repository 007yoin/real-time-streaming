package com.live_stream.domain.cameratype;

import com.live_stream.domain.cameratype.dto.CameraTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
