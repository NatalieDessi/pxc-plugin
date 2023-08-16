package de.natalie.pxc.listener;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.natalie.pxc.utils.PlayerColorUtils.getPlayerColor;
import static de.natalie.pxc.utils.PlayerColorUtils.setDisplayColor;

public class ColorListener implements Listener {
    @EventHandler
    @SneakyThrows
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        getPlayerColor(player).ifPresent(color -> setDisplayColor(player, color));
    }
}
