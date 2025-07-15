package com.live_stream.domain.cameracategory;

import com.live_stream.domain.cameracategory.dto.CameraCategoryDto;
import com.live_stream.domain.cameracategory.dto.CameraCategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraCategoryService {

    private final CameraCategorySearchService ccss;
    private final CameraCategoryRepository ccr;
    private final CameraCategoryMapper ccm;

    @Transactional
    public CameraCategoryDto save(CameraCategoryDto dto) {
        CameraCategory parent = null;
        if (dto.getParentId() != null) {
            parent = ccss.findById(dto.getParentId());
        }

        CameraCategory entity = CameraCategory.builder()
                .name(dto.getName())
                .cameraCategoryType(dto.getCameraCategoryType())
                .parent(parent)
                .build();

        CameraCategory saved = ccr.save(entity);
        return ccm.toDto(saved);
    }


}
