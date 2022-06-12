package com.themintiest.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Set<Role> roles;

    public UserStatus status;
}
