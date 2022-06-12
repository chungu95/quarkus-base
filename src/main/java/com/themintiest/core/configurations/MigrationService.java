package com.themintiest.core.configurations;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class MigrationService {
    // You can Inject the object if you want to use it manually
    private final Flyway flyway;

    public void checkMigration() {
        // This will print 1.0.0
        System.out.println(flyway.info().current().getVersion().toString());
    }
}