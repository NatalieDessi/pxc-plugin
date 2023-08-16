package de.natalie.pxc.utils;

import de.natalie.pxc.model.PlayerColor;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.natalie.pxc.utils.ConfigUtils.getConfigPath;
import static de.natalie.pxc.utils.ConfigUtils.objectMapper;
import static de.natalie.pxc.utils.bukkit.MessageUtils.component;
import static java.lang.Short.parseShort;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.util.Optional.ofNullable;

@UtilityClass
public final class PlayerColorUtils {
    private static final Path playerColorPath = getConfigPath("player_color.json");

    public static Map<UUID, PlayerColor> getColors() throws IOException {
        if (exists(playerColorPath)) {
            return objectMapper.readValue(readAllBytes(playerColorPath), new PlayerColor.IdTypeReference());
        }
        return new HashMap<>();
    }

    public static void setPlayerColor(final UUID uuid, final PlayerColor playerColor) throws IOException {
        final Map<UUID, PlayerColor> colors = getColors();
        colors.put(uuid, playerColor);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(playerColorPath.toFile(), colors);
    }

    public static Optional<PlayerColor> getPlayerColor(@NonNull final Player player) throws IOException {
        return ofNullable(getColors().get(player.getUniqueId()));
    }

    public static Optional<Color> getColor(final Player player) throws IOException {
        return getPlayerColor(player).map(PlayerColor::getColor);
    }

    public static PlayerColor toPlayerColor(final String red, final String green, final String blue) {
        return new PlayerColor(parseShort(red), parseShort(green), parseShort(blue));
    }

    public static void setDisplayColor(final Player player, final PlayerColor color) {
        player.displayName(component(player.getName(), color.red(), color.green(), color.blue()));
    }
}
