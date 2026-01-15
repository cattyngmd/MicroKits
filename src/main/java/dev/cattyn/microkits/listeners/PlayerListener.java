package dev.cattyn.microkits.listeners;

import dev.cattyn.microkits.api.PlayerManager;
import dev.cattyn.microkits.kit.KitManagerImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final PlayerManager playerManager;
    private final KitManagerImpl kitManager;

    public PlayerListener(PlayerManager playerManager, KitManagerImpl kitManager) {
        this.playerManager = playerManager;
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerManager.removeSelected(event.getPlayer().getUniqueId());
        kitManager.savePlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerManager.removeSelected(event.getPlayer().getUniqueId());
        kitManager.loadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        playerManager.removeSelected(event.getEntity().getUniqueId());
    }
}
