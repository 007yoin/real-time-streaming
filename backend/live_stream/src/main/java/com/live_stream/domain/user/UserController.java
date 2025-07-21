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

    private final UserService us;
    private final UserSearchService uss;

    @PostMapping("/user")
    public UserDto save(@RequestBody UserRequestDto userRequestDto) {
        log.debug("Save user: {}", userRequestDto);
        return us.save(userRequestDto);
    }

    @GetMapping("/user/{userId}")
    public UserDto findByIdNotDeleted(@PathVariable Long userId) {
        log.debug("Find user by id not deleted {}", userId);
        return uss.findByIdNotDeleted(userId);
    }

    @GetMapping("/user/recent")
    public Page<UserDto> getRecentUsers(
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Role role,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("Get recent users");
        return uss.getRecentUsers(loginId, name, role, pageable);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.debug("Delete user {}", userId);
        us.deleteUser(userId);
    }

    @DeleteMapping("/users")
    public void deleteUsers(@RequestBody List<Long> userIds) {
        log.debug("Delete users {}", userIds);
        us.deleteUsers(userIds);
    }

    @PutMapping("/user/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        log.debug("Update user {} with data {}", userId, userRequestDto);
        return us.update(userId, userRequestDto);
    }
}
