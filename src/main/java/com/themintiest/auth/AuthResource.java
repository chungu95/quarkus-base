package com.themintiest.auth;

import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.themintiest.auth.domain.Session;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/sessions")
@Tag(name = "Authentication")
@ApplicationScoped
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;

    @POST
    @PermitAll
    @Path("/login")
    @Operation(summary = "Login", description = "Login and get Bearer jwt token.")
    public Session login(@Context SecurityContext ctx, LoginRequest request) {
        return authService.authenticate(request.getEmail(), request.getPassword());
    }

    @GET
    @Path("/logout")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context SecurityContext ctx) {
        return "OK";
    }
}
