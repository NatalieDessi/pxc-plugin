package de.natalie.pxc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Path.of;
import static org.bukkit.Bukkit.getPluginsFolder;

@UtilityClass
public final class ConfigUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Path configPath = of(getPluginsFolder().getPath(), "pxc");

    static {
        if (!exists(configPath)) {
            try {
                createDirectories(configPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Path getConfigPath(final String filename) {
        return configPath.resolve(filename);
    }
}
