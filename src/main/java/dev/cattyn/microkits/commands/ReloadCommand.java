package dev.cattyn.microkits.commands;

import dev.cattyn.microkits.api.MicroKitsAPI;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@Command("reload")
@Permission("microkits.reload")
public class ReloadCommand {
    @Default
    public static void reload(CommandSender sender) {
        MicroKitsAPI.getProvider().getConfigManager().reload(
                Bukkit.getPluginManager().getPlugin("microkits").getConfig()
        );
    }
}
