package dev.cattyn.microkits.listeners;

import dev.cattyn.microkits.api.player.PlayerManager;
import dev.cattyn.microkits.kit.KitManagerImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerListener(PlayerManager playerManager, KitManagerImpl kitManager) implements Listener {
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
