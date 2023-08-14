package de.natalie.pxc.utils;

import de.natalie.pxc.commands.ICommand;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

@UtilityClass
public final class CommandUtils {
    public static void registerCommands(final JavaPlugin plugin, final ICommand[] commands) {
        stream(commands).forEach(command -> setCommandExecutor(plugin, command));
    }

    public static void setCommandExecutor(final JavaPlugin plugin, final ICommand command) {
        requireNonNull(plugin.getCommand(format("pxc:%s", command.getName()))).setExecutor(command);
    }
}
