package com.live_stream.domain.user.dto;

import com.live_stream.domain.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private String loginId;

    private String password;

    private String name;

    private String description;

    private Role role = Role.USER;

}
