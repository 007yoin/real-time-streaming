package com.userservice.user;

import com.userservice.user.dto.UserDto;
import com.userservice.user.dto.UserMapper;
import com.userservice.user.dto.UserRequestDto;
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

    private final UserSearchService uss;
    private final UserRepository ur;

    private final UserMapper um;

    private final BCryptPasswordEncoder bcpe;

    @Transactional
    public UserDto save(UserRequestDto userRequestDto) {
        User newUser = User.builder()
                .loginId(userRequestDto.getLoginId())
                .password(bcpe.encode(userRequestDto.getPassword()))
                .name(userRequestDto.getName())
                .role(userRequestDto.getRole())
                .build();

        User savedUser = ur.save(newUser);

        return um.userToUserDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        uss.findById(userId).delete();
    }

    @Transactional
    public void deleteUsers(List<Long> userIds) {
        ur.softDeleteByIdIn(userIds);
    }

    @Transactional
    public UserDto update(Long userId, UserRequestDto userRequestDto) {
        User findUser = uss.findById(userId);
        findUser.update(userRequestDto);
        if (userRequestDto.getPassword() != null) {
            findUser.updatePassword(bcpe.encode(userRequestDto.getPassword()));
        }

        return um.userToUserDto(findUser);
    }

}
