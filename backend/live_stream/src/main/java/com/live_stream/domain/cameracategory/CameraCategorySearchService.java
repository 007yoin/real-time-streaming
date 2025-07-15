package com.live_stream.domain.cameracategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraCategorySearchService {

    private final CameraCategoryRepository ccr;

    public CameraCategory findById(Long id) {
        return ccr.findById(id).orElseThrow(
                () -> new IllegalArgumentException("CameraCategory not found with id: " + id)
        );
    }
}
