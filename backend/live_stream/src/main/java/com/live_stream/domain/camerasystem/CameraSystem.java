package com.live_stream.domain.camerasystem;

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
public class CameraSystem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camera_system_id", updatable = false)
    private Long id;

    private String name; // 시스템명

    private String description; // 비고

    private boolean isActive; // 활성 상태

    private boolean isDeleted; // 삭제 여부

}
