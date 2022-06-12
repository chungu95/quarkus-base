package com.themintiest.security;

import io.quarkus.security.identity.CurrentIdentityAssociation;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityIdentityHolder implements ContainerRequestFilter {
    private static final ThreadLocal<SecurityIdentity> SECURITY_IDENTITY_HOLDER = new InheritableThreadLocal<>();

    @Inject
    public CurrentIdentityAssociation association;

    public static SecurityIdentity getIdentity() {
        return SECURITY_IDENTITY_HOLDER.get();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        SECURITY_IDENTITY_HOLDER.set(association.getIdentity());
    }
}
