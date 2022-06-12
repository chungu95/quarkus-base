package com.themintiest.core.entity;

import io.quarkus.security.jpa.RolesValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @RolesValue
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "is_system", nullable = false)
    private boolean system;
}
