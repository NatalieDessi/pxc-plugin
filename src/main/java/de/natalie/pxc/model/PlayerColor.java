package de.natalie.pxc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bukkit.Color;

import java.util.Map;
import java.util.UUID;

import static org.bukkit.Color.fromRGB;

public record PlayerColor(Short red, Short green, Short blue) {
    @JsonIgnore
    public Color getColor() {
        return fromRGB(red(), green(), blue());
    }

    public static class IdTypeReference extends TypeReference<Map<UUID, PlayerColor>> {}
}
