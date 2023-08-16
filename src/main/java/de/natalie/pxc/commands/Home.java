package de.natalie.pxc.commands;


import lombok.extern.java.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static de.natalie.pxc.utils.HomeUtils.getHome;
import static de.natalie.pxc.utils.HomeUtils.setHome;
import static de.natalie.pxc.utils.HomeUtils.teleportToHome;
import static de.natalie.pxc.utils.bukkit.MessageUtils.fileError;
import static de.natalie.pxc.utils.bukkit.MessageUtils.invalidArguments;

@Log
public final class Home implements ICommand {
    @Override public boolean onCommand(@NotNull final CommandSender sender,
                                       @NotNull final Command command,
                                       @NotNull final String label,
                                       final @NotNull String[] args) {
        if (sender instanceof Player player) {
            try {
                switch (args.length) {
                    case 0 -> teleportToHome(player);
                    case 1 -> {
                        if (!computeArguments(args, player)) {
                            invalidArguments.accept(player);
                            return false;
                        }
                    }
                    default -> invalidArguments.accept(player);
                }
            } catch (IOException exception) {
                log.warning(exception.getMessage());
                fileError.accept(player, "home.json", "home");
                return false;
            }
        }
        return true;
    }

    private boolean computeArguments(final String[] args, final Player player) throws IOException {
        return switch (args[0].toLowerCase()) {
            case "set" -> setHome(player);
            case "get" -> getHome(player);
            default -> false;
        };
    }
}
