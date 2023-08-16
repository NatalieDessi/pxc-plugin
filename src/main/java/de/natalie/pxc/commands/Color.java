package de.natalie.pxc.commands;

import de.natalie.pxc.model.PlayerColor;
import lombok.extern.java.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

import static de.natalie.pxc.utils.PlayerColorUtils.getColor;
import static de.natalie.pxc.utils.PlayerColorUtils.setDisplayColor;
import static de.natalie.pxc.utils.PlayerColorUtils.setPlayerColor;
import static de.natalie.pxc.utils.PlayerColorUtils.toPlayerColor;
import static de.natalie.pxc.utils.bukkit.MessageUtils.fileError;
import static de.natalie.pxc.utils.bukkit.MessageUtils.invalidArguments;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendError;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendSuccess;
import static java.lang.Integer.toHexString;
import static java.util.logging.Level.WARNING;
import static java.util.stream.Stream.of;

@Log
public class Color implements ICommand {
    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             final @NotNull String[] args) {
        if (sender instanceof Player player) {
            try {
                if (!computeArguments(args, player)) {
                    invalidArguments.accept(player);
                    return false;
                }
            } catch (IOException exception) {
                fileError.accept(player, "color.json", "color");
                log.log(WARNING, exception.getMessage());
            }
        }
        return true;
    }

    private static boolean computeArguments(final @NotNull String[] args, final Player player) throws IOException {
        return switch (args.length) {
            case 0 -> sendColor(player);
            case 4 -> setColor(args, player);
            default -> false;
        };
    }

    private static boolean sendColor(final Player player) throws IOException {
        final Optional<org.bukkit.Color> optionalColor = getColor(player);
        optionalColor.ifPresentOrElse(color -> sendSuccess("Your selected color is: #%s", toHexString(color.asRGB()).toUpperCase()).accept(player),
                                      () -> sendError("You haven't set a color yet!").accept(player));
        return optionalColor.isPresent();
    }

    private static boolean setColor(final String[] args, final Player player) throws IOException {
        if (areMatching(args)) {
            final PlayerColor color = toPlayerColor(args[1], args[2], args[3]);
            setDisplayColor(player, color);
            setPlayerColor(player.getUniqueId(), color);
            return true;
        }
        invalidArguments.accept(player);
        return false;
    }

    private static boolean areMatching(final String[] args) {
        return args[0].equalsIgnoreCase("set") &&
               of(args[1], args[2], args[3]).allMatch(s -> s.matches("(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])"));
    }
}
