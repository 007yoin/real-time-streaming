package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public Page<UserDto> getRecentUsers(String loginId, String name, Role role, Pageable pageable) {
        return userRepository.findRecentUserDtos(loginId, name, role, pageable);
    }
}
