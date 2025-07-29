package com.rts.cameraservice.domain.cameratype;

import com.rts.cameraservice.domain.cameratype.dto.CameraTypeDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraTypeRepository extends JpaRepository<CameraType, Long> {

    @Query("SELECT new com.rts.cameraservice.domain.cameratype.dto.CameraTypeDto(c.id, c.name) FROM CameraType c")
    List<CameraTypeDto> findAllAsDto();
}
