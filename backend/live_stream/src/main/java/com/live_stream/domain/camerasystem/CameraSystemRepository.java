package com.live_stream.domain.camerasystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraSystemRepository extends JpaRepository<CameraSystem, Long> {

}
