package de.natalie.pxc.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

import static de.natalie.pxc.utils.bukkit.BlockUtils.isTree;
import static de.natalie.pxc.utils.bukkit.MaterialUtils.getSapling;
import static de.natalie.pxc.utils.bukkit.MaterialUtils.isLog;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class TreeListener implements Listener {
    private final JavaPlugin plugin;

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Material type = event.getBlock().getType();

        if (player.isSneaking() && isTree(block) && !block.hasMetadata("placed")) {
            final Optional<Material> optionalSapling = ofNullable((getSapling(type)));
            optionalSapling.ifPresent(sapling -> {
                block.setType(sapling);
                player.playSound(block.getLocation(), Sound.BLOCK_GRASS_PLACE, 1.0f, 1.0f);
                event.setCancelled(true);
            });
        }
    }

    @EventHandler
    public void onBlockPlacement(final BlockPlaceEvent event) {
        if (isLog(event.getBlock().getType())) {
            event.getBlock().setMetadata("placed", new FixedMetadataValue(plugin, true));
        }
    }
}
