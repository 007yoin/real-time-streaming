package com.live_stream.domain.camerasystem;

import com.live_stream.common.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class CameraSystem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camera_system_id", updatable = false)
    private Long id;

    private String name; // 시스템명

    private String description; // 비고

    @Builder.Default
    private boolean isActive = false; // 활성 상태

    @Builder.Default
    private boolean isDeleted = false; // 삭제 여부

}
