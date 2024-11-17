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
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lon")
    private Double longitude;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "inserted_at")
    private Date insertedAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "description")
    private String description;
}
