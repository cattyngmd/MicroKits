package dev.cattyn.microkits.kitcreator;

import dev.cattyn.microkits.MicroKits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class KitCreatorHandler implements Listener {
    private final MicroKits kits;

    public KitCreatorHandler(MicroKits kits) {
        this.kits = kits;
    }

    @EventHandler public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase("KitCreator")) {
            event.getPlayer().closeInventory();
        }
    }

    @EventHandler public void onDrag(InventoryDragEvent event) {
        Inventory inv = event.getInventory();

        if (inv.getHolder() != null || event.getInventorySlots().size() <= 1)
            return;

        event.setCancelled(true);
        Bukkit.getScheduler().runTask(kits, () -> {
            event.setCursor(event.getOldCursor());
            PlayerInventory inventory = event.getWhoClicked().getInventory();
            ItemStack stack = event.getCursor();
            stack.setAmount(stack.getMaxStackSize());

            for (int i : event.getInventorySlots()) {
                inventory.setItem(i, stack);
            }
        });

    }

    @EventHandler public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTopInventory().isEmpty() || !event.getView().getTitle().equals("KitCreator"))
            return;

        event.getPlayer().getOpenInventory().setCursor(null);
    }

    @EventHandler public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().isEmpty() || !event.getView().getTitle().equals("KitCreator"))
            return;

        if (event.getCurrentItem() == null) {
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                event.setCancelled(true);

                Bukkit.getScheduler().runTask(kits, () -> {
                    event.getWhoClicked().getOpenInventory().setCursor(null);
                });
            }
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                event.setCancelled(true);
                event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
            }
            return;
        }

        Inventory bottom = event.getView().getBottomInventory();

        if (event.getClickedInventory() == bottom) {
            if (event.getClick() == ClickType.SHIFT_LEFT) {
                event.setCancelled(true);
                return;
            } else if (event.getClick() == ClickType.SHIFT_RIGHT) {
                event.setCurrentItem(new ItemStack(Material.AIR));
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
            event.setCancelled(true);
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
