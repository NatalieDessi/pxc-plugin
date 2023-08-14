package de.natalie.pxc.commands;

import org.bukkit.command.CommandExecutor;

public interface ICommand extends CommandExecutor {
    default String getName() {
        return getClass().getSimpleName().toLowerCase();
    }
}
