package dev.cattyn.microkits.config.section;

import dev.cattyn.microkits.api.config.Config;
import org.bukkit.configuration.ConfigurationSection;

public final class MainConfig implements Config {
    private int maxKits;
    private int minKitName, maxKitName;
    private int saveCooldownMs;

    @Override
    public void load(ConfigurationSection config) {
        maxKits = config.getInt("main.max-kits");
        minKitName = config.getInt("main.min-kit-name");
        maxKitName = config.getInt("main.max-kit-name");
        saveCooldownMs = config.getInt("main.save-cooldown-ms");
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

    public int saveCooldownMs() {
        return saveCooldownMs;
    }
}
