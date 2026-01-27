package dev.cattyn.microkits.config.section;

import dev.cattyn.microkits.api.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class KitCreatorConfig implements Config {
    private List<ItemStack> items = Collections.emptyList();

    @Override
    public void load(ConfigurationSection config) {
        ConfigurationSection section = config.getConfigurationSection("kitcreator");

        if (section == null) {
            items = Collections.emptyList();
            return;
        }

        items = section.getKeys(false).stream()
                .map(section::getItemStack)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<ItemStack> items() {
        return Collections.unmodifiableList(items);
    }
}
