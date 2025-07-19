package com.xmap_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "inserted_at")
    private Date insertedAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
