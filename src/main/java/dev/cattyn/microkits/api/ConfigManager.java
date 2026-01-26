package dev.cattyn.microkits.api;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigManager {
    <T extends Config> T get(Class<T> configClass);

    void loadAll(ConfigurationSection configuration);
}
