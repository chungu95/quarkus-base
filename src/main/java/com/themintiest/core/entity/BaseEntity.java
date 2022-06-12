package com.themintiest.core.entity;

import com.themintiest.security.SecurityIdentityHolder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntity.AuditListener.class)
public class BaseEntity extends PanacheEntityBase {
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_time")
    private Instant updatedTime = Instant.now();

    @Column(name = "updated_by")
    private String updatedBy;

    public static class AuditListener {
        private String currentIdentityName() {
            SecurityIdentity identity = SecurityIdentityHolder.getIdentity();
            return identity != null ? identity.getPrincipal().getName() : "System";
        }

        @PrePersist
        private void forCreate(BaseEntity entity) {
            Instant now = Instant.now();
            entity.createdTime = now;
            entity.createdBy = currentIdentityName();
            entity.updatedTime = now;
        }

        @PreUpdate
        private void forUpdate(BaseEntity entity) {
            entity.updatedTime = Instant.now();
            entity.updatedBy = currentIdentityName();
        }
    }
}
