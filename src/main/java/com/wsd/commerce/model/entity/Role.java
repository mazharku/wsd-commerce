package com.wsd.commerce.model.entity;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
@Entity
public class Role {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name",nullable = false)
    private String roleName;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
