package de.natalie.pxc.commands;

import de.natalie.pxc.utils.bukkit.BukkitUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public final class Spawn implements ICommand {
    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             final @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.teleport(BukkitUtils.getSpawn());
            player.sendMessage(text("successfully teleported to spawn"));
        }
        return true;
    }
}
