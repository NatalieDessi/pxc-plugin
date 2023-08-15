package de.natalie.pxc;

import de.natalie.pxc.commands.Home;
import de.natalie.pxc.commands.ICommand;
import de.natalie.pxc.commands.Spawn;
import de.natalie.pxc.listener.CropListener;
import de.natalie.pxc.listener.TreeListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static de.natalie.pxc.utils.CommandUtils.registerCommands;
import static java.util.Arrays.stream;
import static org.bukkit.Bukkit.getPluginManager;

public final class Plugin extends JavaPlugin {
    private static final ICommand[] commands = new ICommand[]{
            new Spawn(),
            new Home()
    };

    private final Listener[] listeners = new Listener[]{
            new TreeListener(this),
            new CropListener()
    };

    @Override
    public void onEnable() {
        stream(listeners).forEach(listener -> getPluginManager().registerEvents(listener, this));
        registerCommands(this, commands);
    }


    @Override
    public void onDisable() {
        // nothing to disable
    }
}
