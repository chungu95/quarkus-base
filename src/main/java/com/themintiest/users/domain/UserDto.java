package com.themintiest.users.domain;

import lombok.Getter;
import lombok.Setter;
import com.themintiest.core.domain.UserStatus;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private UserStatus status;
}
