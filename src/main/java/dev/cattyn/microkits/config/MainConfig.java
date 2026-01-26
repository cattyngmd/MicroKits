package dev.cattyn.microkits.config;

import dev.cattyn.microkits.api.Config;
import org.bukkit.configuration.ConfigurationSection;

public class MainConfig implements Config {
    private int maxKits;
    private int minKitName, maxKitName;

    @Override
    public void load(ConfigurationSection config) {
        maxKits = config.getInt("main.max-kits");
        minKitName = config.getInt("main.min-kit-name");
        maxKitName = config.getInt("main.max-kit-name");
    }

    public int maxKits() {
        return maxKits;
    }

    public int minKitName() {
        return minKitName;
    }

    public int maxKitName() {
        return maxKitName;
    }
}
