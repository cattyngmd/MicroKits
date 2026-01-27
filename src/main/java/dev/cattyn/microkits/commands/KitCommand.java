package dev.cattyn.microkits.commands;

import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.api.MicroKitsAPI;
import dev.cattyn.microkits.api.MicroKitsProvider;
import dev.cattyn.microkits.config.MainConfig;
import dev.cattyn.microkits.kit.PlayerKit;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static dev.cattyn.microkits.utils.CommandUtil.error;

@Command("kit")
@Permission("microkits.kit")
public class KitCommand {
    @Subcommand("save")
    public static void save(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        MicroKitsProvider provider = MicroKitsAPI.getProvider();
        MainConfig config = provider.getConfigManager().get(MainConfig.class);

        if (!(sender instanceof Player player)) return;
        List<Kit> kits = provider.getKits().get(player.getUniqueId());

        if (kits.size() >= config.maxKits()) {
            error("Too many kits!");
        }

        if (name.length() < config.minKitName()) {
            error("Kit name is too short!");
        }

        if (name.length() > config.maxKitName()) {
            error("Kit name is too long!");
        }

        if (provider.getKits().isOnCooldown(player.getUniqueId(), config.saveCooldownMs())) {
            error("Too fast! Slow it down!");
        }

        PlayerKit kit = new PlayerKit(name);
        int i = 0;
        for (ItemStack stack : player.getInventory()) {
            if (stack != null) {
                kit.getItems().put(i, stack.clone());
            }
            i++;
        }
        provider.getKits().save(player.getUniqueId(), kit);
    }

    @Subcommand("delete")
    public static void delete(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        MicroKitsProvider provider = MicroKitsAPI.getProvider();

        if (!(sender instanceof Player player)) return;
        boolean removed = provider.getKits().remove(player.getUniqueId(), name);

        if (!removed) {
            error("Kit was not found.");
        }
    }

    @Subcommand("load")
    public static void loadSub(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        load(sender, name);
    }

    @Default
    public static void load(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        MicroKitsProvider provider = MicroKitsAPI.getProvider();

        if (!(sender instanceof Player player)) return;

        if (provider.getPlayers().didSelect(player.getUniqueId())) {
            error("You have already selected a kit!");
        }

        Kit kit = provider.getKits().get(player.getUniqueId(), name);
        if (kit == null) {
            error("Kit not found!");
        }

        provider.getPlayers().select(player.getUniqueId(), kit);
        kit.getItems().forEach((i, s) -> player.getInventory().setItem(i, s));
    }
}
