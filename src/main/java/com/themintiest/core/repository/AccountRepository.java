package com.themintiest.core.repository;

import com.themintiest.core.domain.UserStatus;
import com.themintiest.core.entity.Account;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AccountRepository extends BaseRepository<Account, Long> {
    public Optional<Account> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Account> findByEmailAndStatus(String email, UserStatus status) {
        Parameters parameters = Parameters.with("email", email).and("status", status);
        return find("email = :email and status = :status", parameters).firstResultOptional();
    }
}
