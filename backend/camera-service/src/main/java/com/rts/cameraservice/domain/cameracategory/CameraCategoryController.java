package com.rts.cameraservice.domain.cameracategory;

import com.rts.cameraservice.domain.cameracategory.dto.CameraCategoryDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CameraCategoryController {

    private final CameraCategoryService ccs;
    private final CameraCategorySearchService ccss;

    @PostMapping("/camCategory")
    public CameraCategoryDto save(@RequestBody CameraCategoryDto cameraCategoryDto) {
        log.debug("Save camera category: {}", cameraCategoryDto);
        return ccs.save(cameraCategoryDto);
    }

    @GetMapping("/camCategory/large")
    public List<CameraCategoryDto> getLargeCategories() {
        log.debug("Get large categories");
        return ccss.getLargeCategories();
    }

    @GetMapping("/camCategory/medium/{largeCategoryId}")
    public List<CameraCategoryDto> getMediumCategoriesInLarge(@PathVariable Long largeCategoryId) {
        log.debug("Get medium categories in large: {}", largeCategoryId);
        return ccss.getMediumCategoriesInLarge(largeCategoryId);
    }

    @GetMapping("/camCategory/small/{mediumCategoryId}/{largeCategoryId}")
    public List<CameraCategoryDto> getSmallCategoriesInMediumAndLarge(@PathVariable Long mediumCategoryId,
                                                                      @PathVariable Long largeCategoryId) {
        log.debug("Get small categories in medium: {}, large: {}", mediumCategoryId, largeCategoryId);
        return ccss.getSmallCategoriesInMediumAndLarge(mediumCategoryId, largeCategoryId);
    }

}
