package com.live_stream.test;

import com.live_stream.domain.camera.CameraService;
import com.live_stream.domain.camera.dto.CameraInsertDto;
import com.live_stream.domain.cameracategory.CameraCategoryService;
import com.live_stream.domain.cameracategory.dto.CameraCategoryDto;
import com.live_stream.domain.cameratype.CameraTypeService;
import com.live_stream.domain.cameratype.dto.CameraTypeDto;
import com.live_stream.domain.user.UserService;
import com.live_stream.domain.user.dto.UserRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.live_stream.domain.cameracategory.CameraCategoryType.*;
import static com.live_stream.domain.user.Role.ADMIN;
import static com.live_stream.domain.user.Role.USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitService {

    private final UserService us;
    private final CameraService cs;

    private final CameraTypeService cts;
    private final CameraCategoryService ccs;

    @PostConstruct
    public void init() {
        userInit();

        cameraTypeInit();
        cameraCategoryInit();
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

        us.save(userRequestDto1);
        us.save(userRequestDto2);

        for (int i = 0; i < 20; ++i) {
            UserRequestDto dto = new UserRequestDto();
            dto.setLoginId("user" + i);
            dto.setPassword("user");
            dto.setName("user");
            dto.setRole(USER);
            us.save(dto);
        }

    }

    private void cameraTypeInit() {
        CameraTypeDto ctDto1 = new CameraTypeDto(
                null, "도로"
        );
        CameraTypeDto ctDto2 = new CameraTypeDto(
                null, "하천"
        );
        CameraTypeDto ctDto3 = new CameraTypeDto(
                null, "학교앞"
        );

        cts.save(ctDto1);
        cts.save(ctDto2);
        cts.save(ctDto3);
    }

    private void cameraCategoryInit() {
        CameraCategoryDto catDto1 = new CameraCategoryDto(
                null, LARGE, null, "유관기관"
        );

        CameraCategoryDto savedCategory1 = ccs.save(catDto1);

        CameraCategoryDto catDto2 = new CameraCategoryDto(
                null, MEDIUM, savedCategory1.getCategoryId(), "용인교통"
        );

        CameraCategoryDto catDto22 = new CameraCategoryDto(
                null, MEDIUM, savedCategory1.getCategoryId(), "수원교통"
        );

        CameraCategoryDto savedCategory2 = ccs.save(catDto2);
        ccs.save(catDto22);

        CameraCategoryDto catDto3 = new CameraCategoryDto(
                null, SMALL, savedCategory2.getCategoryId(), "수지구"
        );

        CameraCategoryDto catDto33 = new CameraCategoryDto(
                null, SMALL, savedCategory2.getCategoryId(), "기흥구"
        );

        ccs.save(catDto3);
        ccs.save(catDto33);
    }

    private void cameraInit() {
        CameraInsertDto cameraInsertDto1 = new CameraInsertDto(
                "흥덕교",
                "흥덕교 부근",
                1L, 4L,
                "http://211.249.12.147:1935/live/video69.stream/playlist.m3u8",
                "주소", 37.2716932, 127.0910779
        );

        CameraInsertDto cameraInsertDto2 = new CameraInsertDto(
                "죽전육교",
                "죽전육교 부근",
                1L, 4L,
                "http://211.249.12.147:1935/live/video86.stream/playlist.m3u8",
                "도로", 37.32723616, 127.13377143
        );

        CameraInsertDto cameraInsertDto3 = new CameraInsertDto(
                "화운사입구3",
                "화운사입구3 부근",
                1L, 4L,
                "http://211.249.12.147:1935/live/video34.stream/playlist.m3u8",
                "도로", 37.2541872, 127.1660996
        );

        CameraInsertDto cameraInsertDto4 = new CameraInsertDto(
                "호수공원삼거리",
                "호수공원삼거리 부근",
                1L, 4L,
                "http://211.249.12.147:1935/live/video22.stream/playlist.m3u8",
                "도로", 37.2757328, 127.1481881
        );

        CameraInsertDto cameraInsertDto5 = new CameraInsertDto(
                "풍덕천사거리",
                "풍덕천사거리 부근",
                1L, 4L,
                "http://211.249.12.147:1935/live/video9.stream/playlist.m3u8",
                "도로", 37.3243, 127.1027
        );

        cs.save(cameraInsertDto1);
        cs.save(cameraInsertDto2);
        cs.save(cameraInsertDto3);
        cs.save(cameraInsertDto4);
        cs.save(cameraInsertDto5);

    }
}
