package dev.cattyn.microkits.players;

import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.api.PlayerManager;

import java.util.*;

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
}
