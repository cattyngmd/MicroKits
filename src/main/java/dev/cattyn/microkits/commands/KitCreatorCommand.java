package dev.cattyn.microkits.commands;

import dev.cattyn.microkits.api.MicroKitsAPI;
import dev.cattyn.microkits.config.section.KitCreatorConfig;
import dev.cattyn.microkits.kitcreator.KitCreatorInventory;
import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Alias("creator")
@Command("kitcreator")
@Permission("microkits.kitcreator")
public class KitCreatorCommand {
    @Default
    public static void creator(CommandSender sender) {
        Player player = (Player) sender;
        List<ItemStack> kitStacks = MicroKitsAPI.getProvider().getConfigManager().get(KitCreatorConfig.class).items();
        int size = (int) (Math.max(Math.ceil(kitStacks.size() / 9f), 1) * 9);

        InventoryHolder holder = new KitCreatorInventory(size);
        Inventory inventory = holder.getInventory();
        ItemStack[] stacks = new ItemStack[size];
        for (int i = 0; i < kitStacks.size(); i++) {
            stacks[i] = kitStacks.get(i).clone();
        }

        inventory.setContents(stacks);
        player.openInventory(inventory);
    }
}
