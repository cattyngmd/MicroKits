package dev.cattyn.microkits.api.player;

import dev.cattyn.microkits.api.kit.Kit;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerManager {
    Kit getSelected(UUID player);

    void removeSelected(UUID player);

    void select(UUID player, Kit kit);

    boolean isKitCreatorOpen(Player player);

    default boolean didSelect(UUID uuid) {
        return getSelected(uuid) != null;
    }

    default Kit getSelected(Player player) {
        return getSelected(player.getUniqueId());
    }
}
