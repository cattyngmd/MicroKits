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
    public static final int SIZE = 9 * 6;

    @Default
    public static void creator(CommandSender sender) {
        Player player = (Player) sender;
        List<ItemStack> kitStacks = KitCreatorItems.getStacks();
        Inventory inventory = Bukkit.createInventory(null, SIZE, "KitCreator");
        ItemStack[] stacks = new ItemStack[SIZE];
        for (int i = 0; i < kitStacks.size(); i++) {
            stacks[i] = kitStacks.get(i).clone();
        }

        inventory.setContents(stacks);
        player.openInventory(inventory);
    }
}
