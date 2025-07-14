package com.live_stream.domain.camera;

import com.live_stream.domain.camera.dto.CameraDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
    @Query("""
            SELECT new com.live_stream.domain.camera.dto.CameraDto(
                c.id, c.name, c.description, c.systemName,
                c.categoryLarge, c.categoryMedium, c.categorySmall,
                c.streamingUrl, c.cameraType, c.address,
                c.latitude, c.longitude, c.status, c.isDeleted
            )
            FROM Camera c
            WHERE c.isDeleted = false
            """)
    Page<CameraDto> findRecentCameraDtos(Pageable pageable);


}
