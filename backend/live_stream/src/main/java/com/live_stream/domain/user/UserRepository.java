package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    @Query("""
                SELECT new com.live_stream.domain.user.dto.UserDto(
                    u.loginId, u.name, u.role, u.isDeleted
                )
                FROM User u
                WHERE u.isDeleted = false
                  AND (:loginId IS NULL OR u.loginId LIKE CONCAT('%', :loginId, '%'))
                  AND (:name IS NULL OR u.name LIKE CONCAT('%', :name, '%'))
                  AND (:role IS NULL OR u.role = :role)
            """)
    Page<UserDto> findRecentUserDtos(
            @Param("loginId") String loginId,
            @Param("name") String name,
            @Param("role") Role role,
            Pageable pageable
    );


}
