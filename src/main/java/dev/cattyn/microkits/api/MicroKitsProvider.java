package dev.cattyn.microkits.api;

import dev.cattyn.microkits.api.config.ConfigManager;
import dev.cattyn.microkits.api.kit.KitManager;
import dev.cattyn.microkits.api.player.PlayerManager;

public interface MicroKitsProvider {
    KitManager getKits();

    PlayerManager getPlayers();

    ConfigManager getConfigManager();
}
