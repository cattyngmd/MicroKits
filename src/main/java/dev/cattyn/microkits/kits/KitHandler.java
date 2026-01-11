package dev.cattyn.microkits.kits;

import dev.cattyn.microkits.MicroKits;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KitHandler implements Listener {
    private final MicroKits main;
    private final KitManagerImpl manager;

    public KitHandler(MicroKits main, KitManagerImpl manager) {
        this.main = main;
        this.manager = manager;
    }

    @EventHandler
    public synchronized void onPlayerJoin(PlayerJoinEvent event) {
        manager.loadData(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public synchronized void onPlayerQuit(PlayerQuitEvent event) {
        manager.saveData(event.getPlayer().getUniqueId());
    }
}
