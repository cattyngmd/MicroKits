package dev.cattyn.microkits.kits;

import dev.cattyn.microkits.MicroKits;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KitHandler implements Listener {
    private final MicroKits main;
    private final KitManager manager;

    public KitHandler(MicroKits main, KitManager manager) {
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

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().isEmpty() || !event.getView().getTitle().equals("KitCreator"))
            return;


    }
}
