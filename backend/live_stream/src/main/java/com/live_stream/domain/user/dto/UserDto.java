package com.live_stream.domain.user.dto;

import com.live_stream.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String loginId;

    private String name;

    private Role role;

    private boolean isDeleted;

}
