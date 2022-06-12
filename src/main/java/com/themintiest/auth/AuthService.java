package com.themintiest.auth;

import com.themintiest.auth.domain.Session;
import com.themintiest.core.domain.Account;
import com.themintiest.core.domain.UserStatus;
import com.themintiest.core.entity.Profile;
import com.themintiest.core.mappers.ProfileMapper;
import com.themintiest.users.AccountService;
import com.themintiest.users.UserService;
import io.quarkus.security.credential.PasswordCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.smallrye.jwt.build.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
@JBossLog
@RequiredArgsConstructor
public class AuthService {

    private final IdentityProviderManager identityProviderManager;
    private final UserService userService;
    private final AccountService accountService;

    public Session authenticate(String email, String password) {
        SecurityIdentity identity = identityProviderManager.authenticateBlocking(
                new UsernamePasswordAuthenticationRequest(email, new PasswordCredential(password.toCharArray())));
        Account account = accountService.getAccountByEmailAndStatus(email, UserStatus.ACTIVE);
        Profile profile = userService.getByEmail(identity.getPrincipal().getName());
        return createSession(identity.getRoles(), profile);
    }

//    public void changePassword(String username, String currentPassword, String newPassword) throws AuthException {
//        try {
//            identityProviderManager.authenticateBlocking(
//                    new UsernamePasswordAuthenticationRequest(username, new PasswordCredential(currentPassword.toCharArray())));
//        } catch (AuthenticationFailedException e) {
//            throw new RuntimeException("abc");
//        }
//        userService.changePassword(username, newPassword);
//    }

    private Session createSession(Set<String> roles, Profile profile) {
        return Session.builder()
                .authToken(Jwt.upn(profile.username)
                        .groups(roles)
                        .claim(Claims.sub.name(), profile.username)
                        .claim(Claims.email.name(), profile.email)
                        .claim(Claims.given_name.name(), profile.firstName)
                        .claim(Claims.family_name.name(), profile.lastName)
                        .sign())
                .profile(ProfileMapper.INSTANCE.mapFromUserToUserProfile(profile))
                .build();
    }

    private Set<String> getUserRoles(Set<String> userRoles) {
        return userRoles;
    }
}
