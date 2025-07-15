package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import com.live_stream.domain.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository ur;
    private final UserMapper um;

    public User findById(Long userId) {
        return ur.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + userId)
        );
    }

    public UserDto findByIdNotDeleted(Long userId) {
        User findUser = ur.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + userId)
        );

        return um.userToUserDto(findUser);
    }

    public Page<UserDto> getRecentUsers(String loginId, String name, Role role, Pageable pageable) {
        return ur.findRecentUserDtos(loginId, name, role, pageable);
    }
}
