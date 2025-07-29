package com.rts.cameraservice.domain.camera;

public enum CameraStatus {
    // 대기, 처리중, 정지. 비활성화, 에이전트 정지
    PENDING,
    PROCESSING,
    STOPPED,
    DISABLED,
    AGENT_STOPPED
}
