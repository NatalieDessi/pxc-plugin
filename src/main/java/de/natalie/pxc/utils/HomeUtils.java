package de.natalie.pxc.utils;

import de.natalie.pxc.model.HomeLocation;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static de.natalie.pxc.utils.ConfigUtils.getConfigPath;
import static de.natalie.pxc.utils.ConfigUtils.objectMapper;
import static de.natalie.pxc.utils.bukkit.MessageUtils.component;
import static de.natalie.pxc.utils.bukkit.MessageUtils.send;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendError;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendSuccess;
import static de.natalie.pxc.utils.bukkit.MessageUtils.success;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.util.Optional.ofNullable;

@UtilityClass
public final class HomeUtils {
    private static final Consumer<Player> homeSetSuccessful = sendSuccess("Successfully set your home!");
    private static final Path homePath = getConfigPath("home.json");
    private static final BiConsumer<Player, HomeLocation> homeSpot = (player, location) -> send(success("Your home location is: '").append(component("world: %s, x: %.3f, y: %.3f, z: %.3f", location.world(), location.x(), location.y(), location.z()))
                                                                                                                                   .append(success("'"))).accept(player);
    private static final Consumer<Player> noHomeFound = sendError("Cannot teleport to home since you haven't set one. Use /home set for setting a home!");
    private static final Consumer<Player> teleportSuccessful = sendSuccess("Successfully teleported to your home!");

    public static Map<UUID, HomeLocation> getHomes() throws IOException {
        if (exists(homePath)) {
            return objectMapper.readValue(readAllBytes(homePath), new HomeLocation.IdTypeReference());
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

    public static boolean setHome(final Player player) throws IOException {
        final HomeLocation homeLocation = toHomeLocation(player.getLocation());
        setHome(player.getUniqueId(), homeLocation);
        homeSetSuccessful.accept(player);
        return true;
    }

    public static boolean getHome(final Player player) throws IOException {
        final Optional<HomeLocation> optionalHomeLocation = ofNullable(getHomeLocation(player));
        optionalHomeLocation.ifPresentOrElse(homeLocation -> homeSpot.accept(player, homeLocation),
                                             () -> noHomeFound.accept(player));
        return optionalHomeLocation.isPresent();
    }

    public static void teleportToHome(final Player player) throws IOException {
        final Optional<Location> optionalLocation = ofNullable(getHomes().get(player.getUniqueId())).map(HomeLocation::getLocation);
        optionalLocation.ifPresentOrElse(location -> {
            player.teleport(location);
            teleportSuccessful.accept(player);
        }, () -> noHomeFound.accept(player));
    }
}
