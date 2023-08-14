package de.natalie.pxc;

import de.natalie.pxc.commands.Home;
import de.natalie.pxc.commands.ICommand;
import de.natalie.pxc.commands.Spawn;
import org.bukkit.plugin.java.JavaPlugin;

import static de.natalie.pxc.utils.CommandUtils.registerCommands;

public final class Plugin extends JavaPlugin {
    private static final ICommand[] commands = new ICommand[]{
            new Spawn(),
            new Home()
    };

    @Override
    public void onEnable() {
        registerCommands(this, commands);
    }


    @Override
    public void onDisable() {

    }
}
