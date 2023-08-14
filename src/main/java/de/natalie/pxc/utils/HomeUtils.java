package de.natalie.pxc.utils;

import de.natalie.pxc.model.HomeLocation;
import de.natalie.pxc.model.HomeTypeReference;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.natalie.pxc.utils.ConfigUtils.getConfigPath;
import static de.natalie.pxc.utils.ConfigUtils.objectMapper;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;

@UtilityClass
public final class HomeUtils {
    private static final Path homePath = getConfigPath("home.json");

    public static Map<UUID, HomeLocation> getHomes() throws IOException {
        if (exists(homePath)) {
            return objectMapper.readValue(readAllBytes(homePath), new HomeTypeReference());
        }
        return new HashMap<>();
    }

    public static void setHome(final UUID uuid, final HomeLocation homeLocation) throws IOException {
        final Map<UUID, HomeLocation> homes = getHomes();
        homes.put(uuid, homeLocation);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(homePath.toFile(), homes);
    }

    @Nullable
    public static HomeLocation getHomeLocation(final Player player) throws IOException {
        return getHomes().get(player.getUniqueId());
    }

    public static HomeLocation toHomeLocation(final Location location) {
        return new HomeLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }
}
