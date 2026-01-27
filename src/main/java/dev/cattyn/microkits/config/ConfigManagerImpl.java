package dev.cattyn.microkits.config;

import dev.cattyn.microkits.api.config.Config;
import dev.cattyn.microkits.api.config.ConfigManager;
import dev.cattyn.microkits.config.section.KitCreatorConfig;
import dev.cattyn.microkits.config.section.MainConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public final class ConfigManagerImpl implements ConfigManager {
    private final Map<Class<? extends Config>, Config> configs = new HashMap<>();

    public ConfigManagerImpl() {
        register(new MainConfig());
        register(new KitCreatorConfig());
    }

    @Override
    public <T extends Config> T get(Class<T> configClass) {
        return (T) configs.get(configClass);
    }

    @Override
    public void reload(ConfigurationSection config) {
        configs.values().forEach(c -> c.load(config));
    }

    private void register(Config config) {
        configs.put(config.getClass(), config);
    }
}
