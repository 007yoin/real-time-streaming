package com.live_stream.domain.cameracategory;

import com.live_stream.domain.cameracategory.dto.CameraCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraCategorySearchService {

    private final CameraCategoryRepository ccr;

    public CameraCategory findById(Long id) {
        return ccr.findById(id).orElseThrow(
                () -> new IllegalArgumentException("CameraCategory not found with id: " + id)
        );
    }

    public List<CameraCategoryDto> getLargeCategories() {
        return ccr.getLargeCategories();
    }

    public List<CameraCategoryDto> getMediumCategoriesInLarge(Long largeCategoryId) {
        return ccr.getMediumCategoriesInLarge(largeCategoryId);
    }

    public List<CameraCategoryDto> getSmallCategoriesInMediumAndLarge(Long mediumCategoryId, Long largeCategoryId) {
        return ccr.getSmallCategoriesInMediumAndLarge(mediumCategoryId, largeCategoryId);
    }
}
