package com.live_stream.domain.camera;

import com.live_stream.common.baseentity.BaseEntity;
import com.live_stream.domain.cameracategory.CameraCategory;
import com.live_stream.domain.camerasystem.CameraSystem;
import com.live_stream.domain.cameratype.CameraType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_category_id", nullable = false)
    private CameraCategory cameraCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_system_id", nullable = false)
    private CameraSystem cameraSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camera_type_id", nullable = false)
    private CameraType cameraType;

    @Column(nullable = false, length = 50) // unique 걸어야함
    private String name; // 스트리밍명

    private String description; // 비고

    private String streamingUrl; // url

    private String address; // 주소

    private Double latitude; // 위도

    private Double longitude; // 경도

    private boolean isActive; // 활성 상태

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CameraStatus status = STOPPED; // 카메라 상태

    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 여부

}
