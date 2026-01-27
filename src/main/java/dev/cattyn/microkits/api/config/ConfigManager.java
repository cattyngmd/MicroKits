package dev.cattyn.microkits.api.config;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigManager {
    <T extends Config> T get(Class<T> configClass);

    void reload(ConfigurationSection configuration);
}
