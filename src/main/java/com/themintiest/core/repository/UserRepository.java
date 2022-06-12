package com.themintiest.core.repository;

import com.themintiest.core.entity.Profile;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository extends BaseRepository<Profile, Long> {
    public Optional<Profile> findByEmail(String email) {
        Parameters parameters = Parameters.with("email", email);
        return find("email = :email", parameters).firstResultOptional();
    }
    public Boolean existsByEmail(String email) {
        return exists("email", email);
    }
}
