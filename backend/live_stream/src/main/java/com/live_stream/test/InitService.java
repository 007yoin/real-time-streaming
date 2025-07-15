package com.live_stream.test;

import com.live_stream.domain.camera.CameraService;
import com.live_stream.domain.camera.dto.CameraInsertDto;
import com.live_stream.domain.user.UserService;
import com.live_stream.domain.user.dto.UserRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.live_stream.domain.user.Role.ADMIN;
import static com.live_stream.domain.user.Role.USER;

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
        UserRequestDto userRequestDto1 = new UserRequestDto();
        userRequestDto1.setLoginId("test");
        userRequestDto1.setPassword("test");
        userRequestDto1.setName("test");
        userRequestDto1.setRole(ADMIN);

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setLoginId("user");
        userRequestDto2.setPassword("user");
        userRequestDto2.setName("user");
        userRequestDto2.setRole(USER);

        userService.saveUser(userRequestDto1);
        userService.saveUser(userRequestDto2);

        for (int i = 0; i < 33; ++i) {
            UserRequestDto dto = new UserRequestDto();
            dto.setLoginId("user" + i);
            dto.setPassword("user");
            dto.setName("user");
            dto.setRole(USER);
            userService.saveUser(dto);
        }

    }

    private void cameraInit() {
        CameraInsertDto cameraInsertDto1 = new CameraInsertDto(
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
        );

        CameraInsertDto cameraInsertDto2 = new CameraInsertDto(
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
        );

        CameraInsertDto cameraInsertDto3 = new CameraInsertDto(
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
        );

        CameraInsertDto cameraInsertDto4 = new CameraInsertDto(
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
        );

        CameraInsertDto cameraInsertDto5 = new CameraInsertDto(
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
        );
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);

        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);
        cameraService.saveCamera(cameraInsertDto1);
        cameraService.saveCamera(cameraInsertDto2);
        cameraService.saveCamera(cameraInsertDto3);
        cameraService.saveCamera(cameraInsertDto4);
        cameraService.saveCamera(cameraInsertDto5);


    }
}
