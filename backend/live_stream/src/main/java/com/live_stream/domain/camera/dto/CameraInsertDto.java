package com.live_stream.domain.camera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraInsertDto {
    private Long id;

    private String name; // 스트리밍명

    private String description; // 비고

    private String systemName; // 시스템명

    private String organizationName; // 기관명

    private String categoryLarge; // 대분류

    private String categoryMedium; // 중분류

    private String categorySmall; // 소분류

    private String streamingUrl; // url

    private String cameraType; // 유형

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도
}
