package dev.cattyn.microkits.config;

import dev.cattyn.microkits.api.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KitCreatorConfig implements Config {
    private final List<ItemStack> items = new ArrayList<>();

    @Override
    public void load(ConfigurationSection config) {
        Set<String> keys = config.getConfigurationSection("kitcreator").getKeys(false);
        for (String key : keys) {
            ItemStack stack = config.getItemStack("kitcreator." + key);
            System.out.println(key + " " + stack);
            if (stack == null) continue;
            items.add(stack);
        }
    }

    public List<ItemStack> items() {
        return Collections.unmodifiableList(items);
    }
}
