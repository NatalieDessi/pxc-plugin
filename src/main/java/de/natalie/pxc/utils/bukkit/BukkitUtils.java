package de.natalie.pxc.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public final class BukkitUtils {
    public static World getWorld() {
        return Bukkit.getWorld("world");
    }

    public static Location getSpawn() {
        return getWorld().getSpawnLocation();
    }
}
