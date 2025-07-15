package com.live_stream.domain.cameratype;

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
}
