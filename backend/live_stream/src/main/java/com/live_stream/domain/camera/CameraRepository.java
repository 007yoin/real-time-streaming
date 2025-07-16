package com.live_stream.domain.camera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long>, CameraRepositoryCustom {

    @Modifying
    @Query("UPDATE Camera c SET c.isDeleted = true WHERE c.id IN :cameraIds")
    void softDeleteByIdIn(@Param("cameraIds") List<Long> cameraIds);

    @Modifying
    @Query("UPDATE Camera c SET c.isActive = true WHERE c.id IN :cameraIds")
    void activeByIdIn(@Param("cameraIds") List<Long> cameraIds);

    @Modifying
    @Query("UPDATE Camera c SET c.isActive = false WHERE c.id IN :cameraIds")
    void deactivateByIdIn(List<Long> cameraIds);
}
