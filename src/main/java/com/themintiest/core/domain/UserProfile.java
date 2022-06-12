package com.themintiest.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String email;
    private String username;

    @Schema(name = "avatar_url")
    private String avatarUrl;

    @Schema(name = "first_name")
    private String firstName;

    @Schema(name = "last_name")
    private String lastName;

    public UserStatus status;
}
