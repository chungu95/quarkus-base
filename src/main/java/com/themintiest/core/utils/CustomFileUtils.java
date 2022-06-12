package com.themintiest.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JBossLog
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomFileUtils {
    public static List<Path> getFileFromFolder(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException exception) {
            log.error(String.format("Error when reading folder with path = %s", path));
            log.error(exception.getMessage());
        }
        return Collections.emptyList();
    }
}
