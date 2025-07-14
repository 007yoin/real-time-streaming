package com.live_stream.domain.camera;

public enum CameraStatus {
    // 대기, 처리중, 정지. 비활성화, 에이전트 정지
    PENDING,        // 대기 상태
    PROCESSING,     // 처리 중 상태
    STOPPED,        // 정지 상태
    DISABLED,       // 비활성화 상태
    AGENT_STOPPED   // 에이전트 정지 상태
    // 추가적인 상태가 필요할 경우 여기에 정의할 수 있습니다.
}
