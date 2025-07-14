package com.live_stream.domain.camera;

import com.live_stream.common.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Camera extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camera_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // 스트리밍명

    private String description; // 비고

    @Column(nullable = false)
    private String systemName; // 시스템명

    @Column(nullable = false)
    private String organizationName; // 기관명

    @Column(nullable = false)
    private String categoryLarge; // 대분류

    private String categoryMedium; // 중분류

    private String categorySmall; // 소분류

    private String streamingUrl; // url

    @Column(nullable = false)
    private String cameraType; // 유형

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도

}
