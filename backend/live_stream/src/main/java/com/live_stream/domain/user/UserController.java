package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import com.live_stream.domain.user.dto.UserJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    @PostMapping("/user")
    public UserDto saveUser(@RequestBody UserJoinDto userJoinDto) {
        log.info("saveUser {}", userJoinDto);

        return userService.saveUser(userJoinDto);
    }

    @GetMapping("/user/recent")
    public Page<UserDto> getRecentUsers(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Role role,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userSearchService.getRecentUsers(loginId, name, role, pageable);
    }
}
