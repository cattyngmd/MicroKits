package dev.cattyn.microkits.kitcreator;

import dev.cattyn.microkits.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class KitCreatorItems {
    private static final List<ItemStack> stacks = new ArrayList<>();

    private static void add(ItemBuilder builder) {
        add(builder.build());
    }

    private static void add(Material material) {
        add(new ItemStack(material, material.getMaxStackSize()));
    }

    private static void add(ItemStack stack) {
        stacks.add(stack);
    }

    public static List<ItemStack> getStacks() {
        return new ArrayList<>(stacks);
    }

    static {
        addArmor(Enchantment.PROTECTION);
        add(new ItemBuilder(Material.NETHERITE_SWORD)
                .enchantDefaults()
                .enchant(Enchantment.FIRE_ASPECT)
                .enchant(Enchantment.SWEEPING_EDGE)
                .enchant(Enchantment.SHARPNESS)
                .enchant(Enchantment.KNOCKBACK)
        );
        add(new ItemBuilder(Material.NETHERITE_PICKAXE)
                .enchant(Enchantment.EFFICIENCY)
                .enchant(Enchantment.FORTUNE)
        );
        add(new ItemBuilder(Material.BOW)
                .enchantDefaults()
                .enchant(Enchantment.PUNCH)
                .enchant(Enchantment.FLAME)
                .enchant(Enchantment.INFINITY)
                .enchant(Enchantment.POWER)
        );
        add(arrow(PotionType.LONG_SWIFTNESS));
        add(arrow(PotionType.LONG_SLOWNESS));

        addArmor(Enchantment.BLAST_PROTECTION);
        add(Material.TOTEM_OF_UNDYING);
        add(Material.ENCHANTED_GOLDEN_APPLE);
        add(Material.END_CRYSTAL);
        add(Material.EXPERIENCE_BOTTLE);
        add(Material.ENDER_PEARL);

        add(Material.ENDER_CHEST);
        add(Material.OBSIDIAN);
        add(Material.CHORUS_FRUIT);
        add(Material.COBWEB);
    }

    private static ItemStack arrow(PotionType type) {
        ItemStack result = new ItemStack(Material.TIPPED_ARROW, 64);
        PotionMeta resultMeta = (PotionMeta) result.getItemMeta();
        resultMeta.setBasePotionType(type);
        result.setItemMeta(resultMeta);
        return result;
    }

    private static void addArmor(Enchantment enchantment) {
        add(new ItemBuilder(Material.NETHERITE_HELMET)
                .enchantDefaults()
                .enchant(Enchantment.RESPIRATION)
                .enchant(Enchantment.AQUA_AFFINITY)
                .enchant(enchantment)
        );
        add(new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                .enchantDefaults()
                .enchant(enchantment)
        );
        add(new ItemBuilder(Material.NETHERITE_LEGGINGS)
                .enchantDefaults()
                .enchant(enchantment)
        );
        add(new ItemBuilder(Material.NETHERITE_BOOTS)
                .enchantDefaults()
                .enchant(enchantment)
                .enchant(Enchantment.FEATHER_FALLING)
                .enchant(Enchantment.DEPTH_STRIDER)
        );
    }
}
