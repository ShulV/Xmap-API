package com.xmap_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "spot")
@Getter
@Setter
public class Spot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lon")
    private Double longitude;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "adding_date")
    private Date addingDate;

    @Column(name = "updating_date")
    private Date updatingDate;

    @Column(name = "description")
    private String description;
}
