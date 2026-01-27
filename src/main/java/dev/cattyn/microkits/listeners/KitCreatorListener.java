package dev.cattyn.microkits.listeners;

import dev.cattyn.microkits.MicroKits;
import dev.cattyn.microkits.kitcreator.KitCreatorController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public record KitCreatorListener(KitCreatorController controller) implements Listener {
    public KitCreatorListener(MicroKits controller) {
        this(new KitCreatorController(controller));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        controller.click(event);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        controller.drag(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        controller.close(event);
    }
}
