package dev.cattyn.microkits.players;

import dev.cattyn.microkits.api.kit.Kit;
import dev.cattyn.microkits.api.player.PlayerManager;
import dev.cattyn.microkits.kitcreator.KitCreatorInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {
    public static final PlayerManagerImpl INSTANCE = new PlayerManagerImpl();

    private final Map<UUID, Kit> selectedKits = new HashMap<>();

    @Override
    public Kit getSelected(UUID player) {
        return selectedKits.get(player);
    }

    @Override
    public void removeSelected(UUID player) {
        selectedKits.remove(player);
    }

    @Override
    public void select(UUID player, Kit kit) {
        selectedKits.put(player, kit);
    }

    @Override
    public boolean isKitCreatorOpen(Player player) {
        return player.getOpenInventory().getTopInventory().getHolder() instanceof KitCreatorInventory;
    }
}
