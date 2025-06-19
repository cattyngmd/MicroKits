package dev.cattyn.microkits.kitcreator;

import dev.cattyn.microkits.MicroKits;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static dev.cattyn.microkits.commands.KitCreatorCommand.SIZE;

public class KitCreatorHandler implements Listener {
    private final MicroKits kits;

    public KitCreatorHandler(MicroKits kits) {
        this.kits = kits;
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory inv = event.getInventory();

        if (inv.getSize() != SIZE || inv.getHolder() != null)
            return;

        event.setCancelled(true);
        Bukkit.getScheduler().runTask(kits, () -> {
            event.setCursor(event.getOldCursor());
            PlayerInventory inventory = event.getWhoClicked().getInventory();
            for (int i : event.getInventorySlots()) {
                inventory.setItem(i, event.getCursor());
            }
        });

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().isEmpty() || !event.getView().getTitle().equals("KitCreator"))
            return;

        if (event.getCurrentItem() == null) {
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                event.setCancelled(true);

                Bukkit.getScheduler().runTask(kits, () -> {
                    event.getWhoClicked().getOpenInventory().setCursor(null);
                });
            }
            return;
        }

        if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
            event.setCancelled(true);
            Inventory bottom = event.getView().getBottomInventory();
            if (event.getClickedInventory() == bottom) {
                bottom.setItem(event.getSlot(), null);
            }
            return;
        }

        ItemStack clone = getItem(event);
        int slot = event.getSlot();

        if (clone == null)
            return;

        Bukkit.getScheduler().runTaskLater(kits, () -> {
            event.getView().setItem(slot, clone);
        }, 1);
    }

    private ItemStack getItem(InventoryClickEvent event) {
        if (event.getCurrentItem() == null)
            return null;


        if (event.getClickedInventory() != event.getView().getTopInventory())
            return null;

        return event.getCurrentItem().clone();
    }
}
