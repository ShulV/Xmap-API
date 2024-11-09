package com.xmap_api.repos;

import com.xmap_api.models.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepo extends JpaRepository<Spot, Long> {
}
