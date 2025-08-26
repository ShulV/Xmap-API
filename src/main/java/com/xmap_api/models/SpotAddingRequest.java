package com.xmap_api.models;

import com.xmap_api.models.status.SpotAddingRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "spot_adding_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotAddingRequest {
    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "spot_name")
    private String spotName;

    @Column(name = "spot_lat")
    private Double spotLatitude;

    @Column(name = "spot_lon")
    private Double spotLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotAddingRequestStatus status;

    @Column(name = "inserted_at")
    private Date insertedAt;

    @Column(name = "accepted_at")
    private Date acceptedAt;

    @Column(name = "spot_description")
    private String spotDescription;

    @Column(name = "comment")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adder_id")
    private User adder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptor_id")
    private User acceptor;

    @ManyToMany
    @JoinTable(
            name = "spot_adding_request_s3_file",
            joinColumns = @JoinColumn(name = "spot_adding_request_id"),
            inverseJoinColumns = @JoinColumn(name = "s3_file_id")
    )
    private List<S3File> s3Files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
}
