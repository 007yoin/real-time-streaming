package com.live_stream.domain.user;

import com.live_stream.domain.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id IN :userIds")
    void softDeleteByIdIn(@Param("userIds") List<Long> userIds);

    @Query("""
                 SELECT new com.live_stream.domain.user.dto.UserDto(
                     u.id, u.loginId, u.name, u.role, u.description, u.isDeleted
                 )
                 FROM User u
                 WHERE u.isDeleted = false
                   AND (:loginId IS NULL OR u.loginId LIKE CONCAT('%', :loginId, '%'))
                   AND (:name IS NULL OR u.name LIKE CONCAT('%', :name, '%'))
                   AND (:role IS NULL OR u.role = :role)
            \s""")
    Page<UserDto> findRecentUserDtos(
            @Param("loginId") String loginId,
            @Param("name") String name,
            @Param("role") Role role,
            Pageable pageable
    );

    Optional<User> findByIdAndIsDeletedFalse(Long userId);
}
