package com.live_stream.domain.camera;

import com.live_stream.common.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.live_stream.domain.camera.CameraStatus.STOPPED;

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
    private String categoryLarge; // 대분류

    private String categoryMedium; // 중분류

    private String categorySmall; // 소분류

    private String streamingUrl; // url

    @Column(nullable = false)
    private String cameraType; // 유형

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CameraStatus status = STOPPED; // 카메라 상태

    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부

}
