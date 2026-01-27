package dev.cattyn.microkits;

import dev.cattyn.microkits.api.*;
import dev.cattyn.microkits.api.config.ConfigManager;
import dev.cattyn.microkits.api.kit.KitManager;
import dev.cattyn.microkits.api.player.PlayerManager;
import dev.cattyn.microkits.commands.KitCommand;
import dev.cattyn.microkits.commands.KitCreatorCommand;
import dev.cattyn.microkits.config.ConfigManagerImpl;
import dev.cattyn.microkits.kit.KitManagerImpl;
import dev.cattyn.microkits.listeners.KitCreatorListener;
import dev.cattyn.microkits.listeners.PlayerListener;
import dev.cattyn.microkits.players.PlayerManagerImpl;
import dev.jorel.commandapi.CommandAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public final class MicroKits extends JavaPlugin implements MicroKitsProvider {
    private final ConfigManager configs = new ConfigManagerImpl();

    private static Path KITS_PATH;

    @Override
    public void onLoad() {
        KITS_PATH = getDataFolder().toPath().resolve("kits");
        KITS_PATH.toFile().mkdirs();

        saveDefaultConfig();

        CommandAPI.registerCommand(KitCommand.class);
        CommandAPI.registerCommand(KitCreatorCommand.class);
    }

    @Override
    public void onEnable() {
        MicroKitsAPI.setProvider(this);
        getConfigManager().reload(getConfig());

        getServer().getPluginManager().registerEvents(new KitCreatorListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(getPlayers(), (KitManagerImpl) getKits()), this);
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public static Path getKitsPath() {
        return KITS_PATH;
    }

    @Override
    public KitManager getKits() {
        return KitManagerImpl.INSTANCE;
    }

    @Override
    public PlayerManager getPlayers() {
        return PlayerManagerImpl.INSTANCE;
    }

    @Override
    public ConfigManager getConfigManager() {
        return configs;
    }
}
