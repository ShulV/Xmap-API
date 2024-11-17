package com.xmap_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "spot")
@Getter
@Setter
public class Spot {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lat", nullable = false)
    private Double latitude;

    @Column(name = "lon", nullable = false)
    private Double longitude;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Column(name = "inserted_at", nullable = false)
    private Date insertedAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "description", nullable = false)
    private String description;
}
