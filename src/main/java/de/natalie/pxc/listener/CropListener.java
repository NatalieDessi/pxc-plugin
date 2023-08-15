package de.natalie.pxc.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static de.natalie.pxc.utils.bukkit.MaterialUtils.getCrop;
import static de.natalie.pxc.utils.bukkit.MaterialUtils.isCrop;
import static de.natalie.pxc.utils.bukkit.MaterialUtils.isGrown;
import static de.natalie.pxc.utils.bukkit.MaterialUtils.resetAge;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.bukkit.Sound.BLOCK_CROP_BREAK;

public class CropListener implements Listener {
    @EventHandler
    public void onInteraction(final PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        if (isNull(block)) {
            return;
        }
        final Material type = block.getType();
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();

        if (!event.getAction().isRightClick() || !isCrop(type) || !isGrown(block)) {
            return;
        }

        for (ItemStack drop : block.getDrops()) {
            inventory.addItem(drop);
        }

        ofNullable(inventory.getItem(getCrop(type).ordinal())).ifPresent(ItemStack::subtract);
        resetAge(block);
        player.playSound(player.getLocation(), BLOCK_CROP_BREAK, 1f, 1f);
    }
}
