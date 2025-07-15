package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import com.live_stream.domain.user.dto.UserMapper;
import com.live_stream.domain.user.dto.UserRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserSearchService userSearchService;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto saveUser(UserRequestDto userRequestDto) {
        User newUser = User.builder()
                .loginId(userRequestDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .name(userRequestDto.getName())
                .role(userRequestDto.getRole())
                .build();

        User savedUser = userRepository.save(newUser);

        return userMapper.userToUserDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userSearchService.findById(userId).delete();
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        for (Long userId : userIds) {
            userSearchService.findById(userId).delete();
        }
    }

    @Transactional
    public UserDto update(Long userId, UserRequestDto userRequestDto) {
        User findUser = userSearchService.findById(userId);
        findUser.update(userRequestDto);
        if (userRequestDto.getPassword() != null) {
            findUser.updatePassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
        }

        return userMapper.userToUserDto(findUser);
    }

}
