package de.natalie.pxc.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

import static java.lang.String.format;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;
import static org.bukkit.Color.GRAY;

@UtilityClass
public final class MessageUtils {
    public static TextComponent component(final String message, final int red, final int green, final int blue) {
        return text(message, color(red, green, blue));
    }

    public static TextComponent component(final String message, final Object... args) {
        return text(format(message, args), color(GRAY.asRGB()));
    }

    public static TextComponent error(final String message) {
        return component(message, 255, 0, 0);
    }

    public static TextComponent success(final String message) {
        return component(message, 0, 255, 0);
    }

    public static Consumer<Player> send(final TextComponent textComponent) {
        return player -> player.sendMessage(textComponent);
    }

    public static Consumer<Player> sendError(final String message) {
        return send(error(message));
    }

    public static Consumer<Player> sendSuccess(final String message) {
        return send(success(message));
    }
}
