//package com.live_stream.domain.streamingSession;
//
//import com.live_stream.common.baseentity.BaseEntity;
//import com.live_stream.domain.camera.Camera;
//import com.live_stream.domain.user.User;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SuperBuilder
//@AllArgsConstructor
//public class StreamingSession extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "streaming_session_id", updatable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "camera_id", nullable = false)
//    private Camera camera;
//
//    @Column(nullable = false)
//    private LocalDateTime startedAt;
//
//    private LocalDateTime endedAt;
//
//    public void endSession() {
//        this.endedAt = LocalDateTime.now();
//    }
//}