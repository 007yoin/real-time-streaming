package com.live_stream.domain.camerasystem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraSystemSearchService {

    private final CameraSystemRepository csr;

    public CameraSystem findById(Long id) {
        return csr.findById(id).orElseThrow(
                () -> new IllegalArgumentException("CameraCategory not found with id: " + id)
        );
    }
}
