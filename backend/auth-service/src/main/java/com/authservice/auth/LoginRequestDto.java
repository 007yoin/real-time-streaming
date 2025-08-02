package com.authservice.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {

    private String loginId;

    private String password;

}
