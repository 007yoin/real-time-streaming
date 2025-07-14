package com.live_stream.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinDto {

    private String loginId;

    private String password;

    private String name;

}
