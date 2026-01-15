package dev.cattyn.microkits.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated
 */
public final class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        return enchant(enchantment, enchantment.getMaxLevel());
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, false); // true позволяет игнорировать ограничения уровней
        return this;
    }

    public ItemBuilder enchantDefaults() {
        enchant(Enchantment.UNBREAKING, 3);
        enchant(Enchantment.MENDING, 1);
        enchant(Enchantment.VANISHING_CURSE, 1);
        return this;
    }

    public ItemBuilder name(String name) {
        meta.setDisplayName(name.replace("&", "§"));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        List<String> loreList = new ArrayList<>();
        for (String line : lore) {
            loreList.add(line.replace("&", "§"));
        }
        meta.setLore(loreList);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> coloredLore = new ArrayList<>();
        for (String line : lore) {
            coloredLore.add(line.replace("&", "§"));
        }
        meta.setLore(coloredLore);
        return this;
    }

    public ItemBuilder unbreakable() {
        meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder hideFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}