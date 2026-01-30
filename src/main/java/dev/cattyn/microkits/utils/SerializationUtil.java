package dev.cattyn.microkits.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class SerializationUtil {
    private SerializationUtil() {
    }

    public static JsonElement serialize(ItemStack stack) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(stack);

            dataOutput.close();
            return new JsonPrimitive(Base64Coder.encodeLines(outputStream.toByteArray()));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack deserialize(JsonElement data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data.getAsString()));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack stack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return stack;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
