package dev.cattyn.microkits.commands;

import com.google.gson.JsonArray;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static dev.cattyn.microkits.Globals.GSON;

@Command("kitcreator")
public class KitCreatorCommand {
    public static final int SIZE = 9 * 6;

    @Default
    public static void creator(CommandSender sender) {
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, SIZE, "KitCreator");
        ItemStack[] stacks = new ItemStack[SIZE];
        Arrays.fill(stacks, new ItemStack(Material.STONE, 64));
        inventory.setContents(stacks);
        player.openInventory(inventory);

        ItemStack stack = new ItemStack(Material.STONE);
        stack.addUnsafeEnchantment(Enchantment.BREACH, 9);
        stack.addUnsafeEnchantment(Enchantment.AQUA_AFFINITY, 1);
//        stack.getItemMeta().setLore(List.of("Nigger"));

        JsonArray array = new JsonArray();
//        array.add(stack.serialize());

        System.out.println(GSON.toJson(stack.serialize()));
    }
}
