package de.natalie.pxc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SleepListener implements Listener {
    @EventHandler
    public void onSleep(final PlayerJoinEvent event) {
        event.getPlayer().setSleepingIgnored(true);
    }
}
