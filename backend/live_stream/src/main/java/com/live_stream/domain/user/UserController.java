package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import com.live_stream.domain.user.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    @PostMapping("/user")
    public UserDto saveUser(@RequestBody UserRequestDto userRequestDto) {
        log.info("saveUser {}", userRequestDto);

        return userService.saveUser(userRequestDto);
    }

    @GetMapping("/user/{userId}")
    public UserDto findByIdNotDeleted(@PathVariable Long userId) {
        log.info("findByIdNotDeleted {}", userId);
        return userSearchService.findByIdNotDeleted(userId);
    }

    @GetMapping("/user/recent")
    public Page<UserDto> getRecentUsers(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Role role,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userSearchService.getRecentUsers(loginId, name, role, pageable);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("deleteUser {}", userId);
        userService.deleteUser(userId);
    }

    @DeleteMapping("/users")
    public void deleteUsers(@RequestBody List<Long> userIds) {
        log.info("deleteUsers {}", userIds);
        userService.deleteUsers(userIds);
    }

    @PatchMapping("/user/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        log.info("updateUser {} with data {}", userId, userRequestDto);

        return userService.update(userId, userRequestDto);
    }
}
