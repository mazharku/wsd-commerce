package com.wsd.commerce.model.entity;

import com.wsd.commerce.model.exceptions.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
public class User {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "email", length = 256, nullable = false)
    private String email;

    @Transient
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.addAll(roles);
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        if (this.roles == null) {
            throw new ResourceNotFoundException("no role is assigned!");
        }
        this.roles.remove(role);
    }

    public Set<Role> getRoles() {
        if (this.roles == null) {
            return new HashSet<>();
        }
        return this.roles;
    }


}
