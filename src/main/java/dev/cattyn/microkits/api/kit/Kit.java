package dev.cattyn.microkits.api.kit;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface Kit {
    String getName();

    Map<Integer, ItemStack> getItems();
}
