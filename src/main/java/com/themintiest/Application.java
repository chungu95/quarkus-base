package com.themintiest;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@JBossLog
@QuarkusMain
@ApplicationScoped
public class Application {
    void onStart(@Observes StartupEvent event) {
        log.info("Starting the application...");
    }

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
