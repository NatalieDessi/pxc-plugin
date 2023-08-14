package de.natalie.pxc.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.block.Block;

import static de.natalie.pxc.utils.bukkit.MaterialUtils.isLog;
import static org.bukkit.Material.DIRT;
import static org.bukkit.block.BlockFace.DOWN;

@UtilityClass
public final class BlockUtils {

    public static boolean isTree(final Block block) {
        return isLog(block.getType()) && isDirtUnder(block);
    }

    private static boolean isDirtUnder(Block block) {
        return block.getRelative(DOWN, 1).getType().equals(DIRT);
    }
}
