package com.live_stream.domain.cameracategory.dto;

import com.live_stream.domain.cameracategory.CameraCategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraCategoryDto {

    private Long categoryId;
    private CameraCategoryType cameraCategoryType;
    private Long parentId;
    private String name;

}
