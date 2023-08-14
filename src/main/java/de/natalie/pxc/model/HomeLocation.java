package de.natalie.pxc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import static java.lang.String.format;
import static net.kyori.adventure.text.Component.text;
import static org.bukkit.Bukkit.getWorld;

public record HomeLocation(String world, Double x, Double y, Double z) {
    @JsonIgnore
    public Location getLocation() {
        return new Location(getWorld(world), x, y, z);
    }

    @JsonIgnore
    public Component asText() {
        return text(format("world: %s, x: %.3f, y: %.3f, z: %.3f", world, x, y, z));
    }
}
