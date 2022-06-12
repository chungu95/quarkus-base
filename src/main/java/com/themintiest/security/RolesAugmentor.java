package com.themintiest.security;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import java.util.Locale;
import java.util.function.Supplier;

@ApplicationScoped
@ActivateRequestContext
@JBossLog
public class RolesAugmentor implements SecurityIdentityAugmentor {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        if (identity.getPrincipal() instanceof JWTCallerPrincipal) {
            return Uni.createFrom().item(build(identity));
        }
        return Uni.createFrom().item(identity);
    }

    private Supplier<SecurityIdentity> build(SecurityIdentity identity) {
        if (identity.isAnonymous()) {
            return () -> identity;
        }
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder()
                .setPrincipal(identity.getPrincipal())
                .addAttributes(identity.getAttributes())
                .addPermissionChecker(identity::checkPermission)
                .addAttribute("locale", new Locale("vn", "VN"))
                .addCredentials(identity.getCredentials())
                .addRoles(identity.getRoles());
        return builder::build;
    }
}
