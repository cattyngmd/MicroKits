package dev.cattyn.microkits.commands;

import dev.cattyn.microkits.kitcreator.KitCreatorItems;
import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Alias("creator")
@Command("kitcreator")
public class KitCreatorCommand {
    @Default
    public static void creator(CommandSender sender) {
        Player player = (Player) sender;
        List<ItemStack> kitStacks = KitCreatorItems.getStacks();
        int size = (int) (Math.ceil(kitStacks.size() / 9f) * 9);

        Inventory inventory = Bukkit.createInventory(null, size, "KitCreator");
        ItemStack[] stacks = new ItemStack[size];
        for (int i = 0; i < kitStacks.size(); i++) {
            stacks[i] = kitStacks.get(i).clone();
        }

        inventory.setContents(stacks);
        player.openInventory(inventory);
    }
}
