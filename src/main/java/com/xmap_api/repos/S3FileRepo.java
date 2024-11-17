package com.xmap_api.repos;

import com.xmap_api.models.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface S3FileRepo extends JpaRepository<S3File, UUID> {
}
