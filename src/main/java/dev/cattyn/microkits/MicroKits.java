package dev.cattyn.microkits;

import dev.cattyn.microkits.commands.KitCommand;
import dev.cattyn.microkits.commands.KitCreatorCommand;
import dev.cattyn.microkits.kitcreator.KitCreatorHandler;
import dev.cattyn.microkits.kits.KitHandler;
import dev.cattyn.microkits.kits.KitManager;
import dev.cattyn.microkits.players.PlayerManager;
import dev.jorel.commandapi.CommandAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public final class MicroKits extends JavaPlugin {
    private static Path KITS_PATH;

    @Override
    public void onLoad() {
        KITS_PATH = getDataFolder().toPath().resolve("kits");
        KITS_PATH.toFile().mkdirs();

        CommandAPI.registerCommand(KitCommand.class);
        CommandAPI.registerCommand(KitCreatorCommand.class);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new KitCreatorHandler(this), this);
        getServer().getPluginManager().registerEvents(new KitHandler(this, KitManager.INSTANCE), this);
        getServer().getPluginManager().registerEvents(PlayerManager.INSTANCE, this);
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public static Path getKitsPath() {
        return KITS_PATH;
    }
}
