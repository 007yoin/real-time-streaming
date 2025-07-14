package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import com.live_stream.domain.user.dto.UserJoinDto;
import com.live_stream.domain.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto saveUser(UserJoinDto userJoinDto) {
        User newUser = User.builder()
                .loginId(userJoinDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(userJoinDto.getPassword()))
                .name(userJoinDto.getName())
                .build();

        User savedUser = userRepository.save(newUser);

        return userMapper.userToUserDto(savedUser);
    }

}
