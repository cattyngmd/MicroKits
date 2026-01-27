package dev.cattyn.microkits.kitcreator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KitCreatorInventory implements InventoryHolder {
    private final Inventory inventory;

    public KitCreatorInventory(int size) {
        this.inventory = Bukkit.createInventory(this, size, "KitCreator");
    }

    @NonNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
