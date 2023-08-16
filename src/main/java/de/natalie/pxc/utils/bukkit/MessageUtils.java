package de.natalie.pxc.utils.bukkit;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.TextComponent;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

import static java.lang.String.format;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;
import static org.bukkit.Color.GRAY;

@UtilityClass
public final class MessageUtils {

    public static final Consumer<Player> invalidArguments = sendError("The arguments you have specified are not valid!");
    public static final TriConsumer<Player, String, String> fileError = (player, file, commandName) -> sendError("Cannot set %s since the %s has errors or does not exist!", commandName, file).accept(player);

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

    public static Consumer<Player> sendError(final String message, final Object... args) {
        return send(error(format(message, args)));
    }

    public static Consumer<Player> sendSuccess(final String message, final Object... args) {
        return send(success(format(message, args)));
    }
}
