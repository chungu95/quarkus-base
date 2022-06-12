package com.themintiest.users.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String avatarUrl;
    private String firstName;
    private String lastName;
}
