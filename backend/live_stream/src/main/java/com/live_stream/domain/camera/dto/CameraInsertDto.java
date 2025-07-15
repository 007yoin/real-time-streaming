package com.live_stream.domain.camera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraInsertDto {
    private String name; // 스트리밍명

    private String description; // 비고

    private Long systemId; // 시스템 ID

    private Long cameraTypeId; // 카메라 유형 ID

    private Long cameraCategoryId; // 카메라 카테고리 ID

    private String streamingUrl; // url

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도
}
