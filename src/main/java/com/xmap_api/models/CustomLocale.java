package com.xmap_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "custom_locale")
@Getter
public class CustomLocale {
    @Id
    @Column(name = "locale_code")
    private String localeCode;

    @Column(name = "description")
    private String description;
}
