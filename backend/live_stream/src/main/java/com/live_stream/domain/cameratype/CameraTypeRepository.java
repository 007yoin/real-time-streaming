package com.live_stream.domain.cameratype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraTypeRepository extends JpaRepository<CameraType, Long> {

}
