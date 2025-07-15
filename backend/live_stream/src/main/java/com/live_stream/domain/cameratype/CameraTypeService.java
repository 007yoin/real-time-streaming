package com.live_stream.domain.cameratype;

import com.live_stream.domain.cameratype.dto.CameraTypeDto;
import com.live_stream.domain.cameratype.dto.CameraTypeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraTypeService {

    private final CameraTypeRepository ctr;
    private final CameraTypeMapper ctm;

    @Transactional
    public CameraTypeDto save(CameraTypeDto cameraTypeDto) {
        CameraType newCameraType = CameraType.builder()
                .name(cameraTypeDto.getName()).build();

        CameraType savedCameraType = ctr.save(newCameraType);

        return ctm.toDto(savedCameraType);
    }

}
