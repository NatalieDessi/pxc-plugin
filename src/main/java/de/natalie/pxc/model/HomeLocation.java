package de.natalie.pxc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getWorld;

public record HomeLocation(String world, Double x, Double y, Double z) {
    @JsonIgnore
    public Location getLocation() {
        return new Location(getWorld(world), x, y, z);
    }

    public static class IdTypeReference extends TypeReference<Map<UUID, HomeLocation>> {}
}
