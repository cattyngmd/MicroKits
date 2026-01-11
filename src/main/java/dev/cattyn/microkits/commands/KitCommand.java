package dev.cattyn.microkits.commands;

import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.kits.KitManagerImpl;
import dev.cattyn.microkits.kits.PlayerKit;
import dev.cattyn.microkits.players.PlayerManagerImpl;
import dev.cattyn.microkits.utils.KitStorageUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static dev.cattyn.microkits.utils.CommandUtil.error;

@Command("kit")
public class KitCommand {
    private static final short MAX_KITS = 10;

    @Subcommand("save")
    public static void save(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        if (!(sender instanceof Player player)) return;

        List<Kit> kits = KitManagerImpl.INSTANCE.get(player.getUniqueId());
        if (kits.size() >= MAX_KITS) {
            error("Too many kits!");
        }

        PlayerKit kit = new PlayerKit(name);
        int i = 0;
        for (ItemStack stack : player.getInventory()) {
            if (stack != null) {
                kit.getItems().put(i, stack.clone());
            }
            i++;
        }
        KitManagerImpl.INSTANCE.save(player.getUniqueId(), kit);
        KitStorageUtil.save(player.getUniqueId());
    }

    @Subcommand("delete")
    public static void delete(CommandSender sender, @AStringArgument String name) throws WrapperCommandSyntaxException {
        if (!(sender instanceof Player player)) return;
        boolean removed = KitManagerImpl.INSTANCE.remove(player.getUniqueId(), name);

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
        if (!(sender instanceof Player player)) return;

        if (PlayerManagerImpl.INSTANCE.didSelect(player.getUniqueId())) {
            error("You have already selected a kit!");
        }

        Kit kit = KitManagerImpl.INSTANCE.get(player.getUniqueId(), name);
        if (kit == null) {
            error("Kit not found!");
        }

        PlayerManagerImpl.INSTANCE.add(player, kit);
        kit.getItems().forEach((i, s) -> player.getInventory().setItem(i, s));
    }
}
