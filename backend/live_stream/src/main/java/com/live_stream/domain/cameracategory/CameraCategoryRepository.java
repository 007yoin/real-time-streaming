package com.live_stream.domain.cameracategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraCategoryRepository extends JpaRepository<CameraCategory, Long> {

}
