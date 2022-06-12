package com.themintiest.users.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class GrantRoleRQ {
    @NotNull
    private Set<String> roles;
}
