package com.themintiest.users;

import com.themintiest.users.domain.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.themintiest.core.constants.Roles;
import com.themintiest.core.query.PageQueryBuilder;
import com.themintiest.core.query.PageQueryParams;
import com.themintiest.core.query.Pageable;
import com.themintiest.users.domain.GrantRoleRQ;
import com.themintiest.users.domain.RegisterRequest;
import com.themintiest.users.domain.UserDto;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Tag(name = "User")
@ApplicationScoped
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    @POST
    @PermitAll
    @Path("/register")
    @Operation(summary = "Register", description = "Create new account")
    public UserDto register(RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @GET
    @RolesAllowed({Roles.USER})
    @Path("/current")
    @Operation(summary = "Get current", description = "Get current user info")
    public UserDto getCurrent() {
        return userService.getCurrentLoginUser();
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Operation(summary = "Get user info by id", description = "Get user info by id")
    public UserDto getUserById(@PathParam("id") Long id) {
        return userService.getUserRSById(id);
    }

    @PUT
    @RolesAllowed({Roles.ADMIN})
    @Path("/{id}/roles")
    @Operation(summary = "Grant role for user", description = "Grant role for user")
    public UserDto grantRole(@PathParam("id") Long id, @Valid GrantRoleRQ grantRoleRQ) {
        return userService.grantRoleForUser(id, grantRoleRQ);
    }

    @PUT
    @RolesAllowed({Roles.USER})
    @Operation(summary = "Update profile", description = "Update profile")
    public UserDto updateProfile(@Valid UpdateProfileRequest updateProfileRequest) {
        return userService.updateMyProfile(updateProfileRequest);
    }

    @GET
    @PermitAll
    @Path("/search")
    @Operation(summary = "Search", description = "Search users")
    public Pageable<UserDto> search(@BeanParam PageQueryParams pageQueryParams) {
        return userService.search(PageQueryBuilder.of(pageQueryParams));
    }

}
