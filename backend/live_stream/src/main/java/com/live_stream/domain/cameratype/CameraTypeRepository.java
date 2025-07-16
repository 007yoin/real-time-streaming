package com.live_stream.domain.cameratype;

import com.live_stream.domain.cameratype.dto.CameraTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraTypeRepository extends JpaRepository<CameraType, Long> {

    @Query("SELECT new com.live_stream.domain.cameratype.dto.CameraTypeDto(c.id, c.name) FROM CameraType c")
    List<CameraTypeDto> findAllAsDto();
}
