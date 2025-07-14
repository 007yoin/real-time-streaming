package com.live_stream.test;

import com.live_stream.domain.camera.CameraService;
import com.live_stream.domain.camera.dto.CameraInsertDto;
import com.live_stream.domain.user.UserService;
import com.live_stream.domain.user.dto.UserJoinDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitService {

    private final UserService userService;
    private final CameraService cameraService;

    @PostConstruct
    public void init() {
        userInit();
        cameraInit();
    }

    private void userInit() {
        userService.saveUser(
                new UserJoinDto("user", "user", "user")
        );
    }

    private void cameraInit() {
        cameraService.saveCamera(
                new CameraInsertDto(
                        null, // ID will be auto-generated
                        "흥덕교",
                        null,
                        "Internet-System#1",
                        "유관기관",
                        "용인교통정보센터",
                        null,
                        "http://211.249.12.147:1935/live/video69.stream/playlist.m3u8",
                        "도로",
                        null,
                        37.2716932,
                        127.0910779
                )
        );

        cameraService.saveCamera(
                new CameraInsertDto(
                        null, // ID will be auto-generated
                        "죽전육교",
                        null,
                        "Internet-System#1",
                        "유관기관",
                        "용인교통정보센터",
                        null,
                        "http://211.249.12.147:1935/live/video86.stream/playlist.m3u8",
                        "도로",
                        null,
                        37.32723616,
                        127.13377143
                )
        );

        cameraService.saveCamera(
                new CameraInsertDto(
                        null, // ID will be auto-generated
                        "화운사입구3",
                        null,
                        "Internet-System#1",
                        "유관기관",
                        "용인교통정보센터",
                        null,
                        "http://211.249.12.147:1935/live/video34.stream/playlist.m3u8",
                        "도로",
                        null,
                        37.2541872,
                        127.1660996
                )
        );

        cameraService.saveCamera(
                new CameraInsertDto(
                        null, // ID will be auto-generated
                        "호수공원삼거리",
                        null,
                        "Internet-System#1",
                        "유관기관",
                        "용인교통정보센터",
                        null,
                        "http://211.249.12.147:1935/live/video22.stream/playlist.m3u8",
                        "도로",
                        null,
                        37.2757328,
                        127.1481881
                )
        );

        cameraService.saveCamera(
                new CameraInsertDto(
                        null, // ID will be auto-generated
                        "풍덕천사거리",
                        null,
                        "Internet-System#1",
                        "유관기관",
                        "용인교통정보센터",
                        null,
                        "http://211.249.12.147:1935/live/video9.stream/playlist.m3u8",
                        "도로",
                        null,
                        37.3243,
                        127.1027
                )
        );

    }
}
