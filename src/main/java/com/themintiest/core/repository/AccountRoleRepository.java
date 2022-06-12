package com.themintiest.core.repository;

import com.themintiest.core.entity.AccountRole;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AccountRoleRepository extends BaseRepository<AccountRole, Long> {
    public List<AccountRole> findByAccountIdAndRoleId(Long accountId, Long roleId) {
        Parameters parameters = Parameters.with("accountId", accountId).and("roleId", roleId);
        return find("accountId = :accountId and roleId = :roleId", parameters).list();
    }

    public List<AccountRole> findByAccountId(Long accountId) {
        return find("accountId", accountId).list();
    }

    public void deleteByAccountId(Long accountId) {
        delete("accountId", accountId);
    }
}
