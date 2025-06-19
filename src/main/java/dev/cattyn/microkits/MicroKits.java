package dev.cattyn.microkits;

import dev.cattyn.microkits.commands.KitCommand;
import dev.cattyn.microkits.commands.KitCreatorCommand;
import dev.cattyn.microkits.kitcreator.KitCreatorHandler;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class MicroKits extends JavaPlugin {

    @Override
    public void onLoad() {
//        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        CommandAPI.registerCommand(KitCommand.class);
        CommandAPI.registerCommand(KitCreatorCommand.class);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new KitCreatorHandler(this), this);
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
