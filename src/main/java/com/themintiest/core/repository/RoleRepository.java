package com.themintiest.core.repository;


import com.themintiest.core.entity.Role;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class RoleRepository extends BaseRepository<Role, Long> {
    public Optional<Role> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public Set<Role> findByNameIn(Set<String> name) {
        return findIn("name", name);
    }

}
