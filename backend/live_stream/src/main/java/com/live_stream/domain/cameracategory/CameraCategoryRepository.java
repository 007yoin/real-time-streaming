package com.live_stream.domain.cameracategory;

import com.live_stream.domain.cameracategory.dto.CameraCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraCategoryRepository extends JpaRepository<CameraCategory, Long> {

    @Query("SELECT new com.live_stream.domain.cameracategory.dto.CameraCategoryDto(c.id, c.cameraCategoryType, " +
            "c.parent.id, c.name) " +
            "FROM CameraCategory c " +
            "WHERE c.cameraCategoryType = com.live_stream.domain.cameracategory.CameraCategoryType.LARGE")
    List<CameraCategoryDto> getLargeCategories();

    @Query("SELECT new com.live_stream.domain.cameracategory.dto.CameraCategoryDto(c.id, c.cameraCategoryType, " +
            "c.parent.id, c.name) " +
            "FROM CameraCategory c " +
            "WHERE c.cameraCategoryType = com.live_stream.domain.cameracategory.CameraCategoryType.MEDIUM " +
            "AND c.parent.id = :parentId")
    List<CameraCategoryDto> getMediumCategoriesInLarge(@Param("parentId") Long parentId);

    @Query("""
             SELECT new com.live_stream.domain.cameracategory.dto.CameraCategoryDto(c.id, c.cameraCategoryType,\s
                    c.parent.id, c.name)
             FROM CameraCategory c
             JOIN c.parent m       \s
             JOIN m.parent l       \s
             WHERE c.cameraCategoryType = com.live_stream.domain.cameracategory.CameraCategoryType.SMALL
               AND m.id = :mediumCategoryId
               AND l.id = :largeCategoryId
            \s""")
    List<CameraCategoryDto> getSmallCategoriesInMediumAndLarge(@Param("mediumCategoryId") Long mediumCategoryId,
                                                               @Param("largeCategoryId") Long largeCategoryId);

}
