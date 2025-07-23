package com.userservice.user;

import com.userservice.baseentity.BaseEntity;
import com.userservice.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.userservice.user.Role.USER;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    private String description; // 비고

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = USER;

    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = false;

    public void delete() {
        this.isDeleted = true;
    }

    public void update(UserRequestDto userRequestDto) {
        this.loginId = userRequestDto.getLoginId();
        this.name = userRequestDto.getName();
        this.description = userRequestDto.getDescription();
        this.role = userRequestDto.getRole();
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }
}
