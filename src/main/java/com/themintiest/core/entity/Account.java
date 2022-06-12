package com.themintiest.core.entity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.Getter;
import lombok.Setter;
import com.themintiest.core.domain.UserStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@UserDefinition
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_seq")
    @SequenceGenerator(name = "accounts_seq", allocationSize = 10)
    public Long id;

    @Column(unique = true)
    public String username;

    @Username
    @Column(unique = true)
    public String email;

    @Password
    public String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public UserStatus status = UserStatus.ACTIVE;

    @Roles
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    public Set<Role> roles;
}
