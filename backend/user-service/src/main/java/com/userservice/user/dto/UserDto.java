package com.userservice.user.dto;

import com.userservice.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String loginId;

    private String name;

    private Role role;

    private String description;

    private boolean isDeleted;

}
