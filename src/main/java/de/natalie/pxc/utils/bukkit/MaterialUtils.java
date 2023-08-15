package de.natalie.pxc.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.List.of;
import static org.bukkit.Material.ACACIA_LOG;
import static org.bukkit.Material.ACACIA_SAPLING;
import static org.bukkit.Material.BEETROOT;
import static org.bukkit.Material.BEETROOTS;
import static org.bukkit.Material.BIRCH_LOG;
import static org.bukkit.Material.BIRCH_SAPLING;
import static org.bukkit.Material.CARROT;
import static org.bukkit.Material.CARROTS;
import static org.bukkit.Material.CHERRY_LOG;
import static org.bukkit.Material.CHERRY_SAPLING;
import static org.bukkit.Material.DARK_OAK_LOG;
import static org.bukkit.Material.DARK_OAK_SAPLING;
import static org.bukkit.Material.JUNGLE_LOG;
import static org.bukkit.Material.JUNGLE_SAPLING;
import static org.bukkit.Material.MANGROVE_LOG;
import static org.bukkit.Material.MANGROVE_PROPAGULE;
import static org.bukkit.Material.OAK_LOG;
import static org.bukkit.Material.OAK_SAPLING;
import static org.bukkit.Material.POTATO;
import static org.bukkit.Material.POTATOES;
import static org.bukkit.Material.SPRUCE_LOG;
import static org.bukkit.Material.SPRUCE_SAPLING;
import static org.bukkit.Material.WHEAT;
import static org.bukkit.Material.WHEAT_SEEDS;

@UtilityClass
public final class MaterialUtils {
    private static final List<Material> logs = of(OAK_LOG,
                                                  DARK_OAK_LOG,
                                                  BIRCH_LOG,
                                                  ACACIA_LOG,
                                                  CHERRY_LOG,
                                                  JUNGLE_LOG,
                                                  SPRUCE_LOG,
                                                  MANGROVE_LOG);

    private static final List<Material> crops = of(BEETROOTS,
                                                   WHEAT,
                                                   POTATOES,
                                                   CARROTS);

    @Nullable
    public static Material getSapling(final Material log) {
        return switch (log) {
            case OAK_LOG -> OAK_SAPLING;
            case DARK_OAK_LOG -> DARK_OAK_SAPLING;
            case BIRCH_LOG -> BIRCH_SAPLING;
            case ACACIA_LOG -> ACACIA_SAPLING;
            case CHERRY_LOG -> CHERRY_SAPLING;
            case JUNGLE_LOG -> JUNGLE_SAPLING;
            case SPRUCE_LOG -> SPRUCE_SAPLING;
            case MANGROVE_LOG -> MANGROVE_PROPAGULE;
            default -> null;
        };
    }

    public static boolean isLog(final Material material) {
        return logs.contains(material);
    }

    public static boolean isCrop(final Material material) {
        return crops.contains(material);
    }

    @Nullable
    public static Material getCrop(final Material material) {
        return switch (material) {
            case BEETROOTS -> BEETROOT;
            case WHEAT -> WHEAT_SEEDS;
            case POTATOES -> POTATO;
            case CARROTS -> CARROT;
            default -> null;
        };
    }

    public static boolean isGrown(final Block block) {
        return block.getBlockData() instanceof Ageable crop && crop.getAge() == crop.getMaximumAge();
    }

    public static void resetAge(final Block block) {
        if (block.getBlockData() instanceof Ageable crop) {
            crop.setAge(0);
            block.setBlockData(crop);
        }
    }
}
