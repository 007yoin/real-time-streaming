package com.userservice.test;

import com.userservice.user.Role;
import com.userservice.user.UserSearchService;
import com.userservice.user.UserService;
import com.userservice.user.dto.UserRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.userservice.user.Role.ADMIN;
import static com.userservice.user.Role.USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitService {

    private final UserService us;
    private final UserSearchService uss;

    @PostConstruct
    public void init() {
        userInit();
    }

    private void userInit() {
        createUserIfNotExists("test", "test", "test", ADMIN);
        createUserIfNotExists("user", "user", "user", USER);

        for (int i = 0; i < 20; ++i) {
            createUserIfNotExists("user" + i, "user", "user", USER);
        }
    }

    private void createUserIfNotExists(String loginId, String password, String name, Role role) {
        try {
            if (!uss.existsByLoginId(loginId)) {  // UserService에 추가해야 함
                UserRequestDto dto = new UserRequestDto();
                dto.setLoginId(loginId);
                dto.setPassword(password);
                dto.setName(name);
                dto.setRole(role);
                us.save(dto);
                log.info("초기 유저 생성 완료: {}", loginId);
            } else {
                log.info("이미 존재하는 유저: {}", loginId);
            }
        } catch (Exception e) {
            log.error("유저 생성 중 오류 발생 - loginId: {}", loginId, e);
        }
    }
}
