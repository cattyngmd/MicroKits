package dev.cattyn.microkits.utils;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class SerializationUtil {
    private SerializationUtil() {
    }

    public static JsonObject serialize(ItemStack stack) {
        return Globals.GSON.toJsonTree(stack.serialize()).getAsJsonObject();
    }

    @SuppressWarnings("unchecked")
    public static ItemStack deserialize(JsonObject data) {
        if (data == null || data.isJsonNull())
            return new ItemStack(Material.AIR);
        return ItemStack.deserialize(Globals.GSON.fromJson(data, Map.class));
    }
}
