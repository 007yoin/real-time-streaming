package com.live_stream.test;

import com.live_stream.domain.user.UserService;
import com.live_stream.domain.user.dto.UserJoinDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitService {

    private final UserService userService;

    @PostConstruct
    public void init() {
        userService.saveUser(
                new UserJoinDto("test", "test", "test")
        );
    }
}
