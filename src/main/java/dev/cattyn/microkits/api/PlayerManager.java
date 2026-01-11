package dev.cattyn.microkits.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerManager {
    Kit getSelected(UUID player);

    default boolean didSelect(UUID uuid) {
        return getSelected(uuid) != null;
    }

    default Kit getSelected(Player player) {
        return getSelected(player.getUniqueId());
    }
}
