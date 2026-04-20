package dev.cattyn.microkits.kit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.cattyn.microkits.api.kit.Kit;
import dev.cattyn.microkits.utils.CompressionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class PlayerKit implements Kit {
    private final Map<Integer, ItemStack> content = new HashMap<>();
    private final String name;

    public PlayerKit(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        return content;
    }

    public static class Serializer {
        public JsonElement serialize(Kit kit) {
            JsonObject object = new JsonObject();
            object.addProperty("name", kit.getName());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (BukkitObjectOutputStream os = new BukkitObjectOutputStream(out)) {
                write(os, kit);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            byte[] contentBytes = CompressionUtil.compress(out.toByteArray());
            String contentString = Base64.getEncoder().encodeToString(contentBytes);

            object.addProperty("base64", contentString);

            return object;
        }

        public Kit deserialize(JsonElement jsonElement) {
            if (!jsonElement.isJsonObject())
                throw new JsonParseException("Not a json object.");

            JsonObject object = jsonElement.getAsJsonObject();

            if (!object.has("name") || !object.has("base64"))
                throw new JsonParseException("Invalid json object.");


            String name = object.get("name").getAsString();
            String contentString = object.get("base64").getAsString();
            byte[] contentBytesCompressed = Base64.getDecoder().decode(contentString);
            byte[] contentBytes;

            try {
                contentBytes = CompressionUtil.decompress(contentBytesCompressed);
            } catch (DataFormatException e) {
                throw new JsonParseException("Failed to decompress", e);
            }

            PlayerKit kit = new PlayerKit(name);
            try (BukkitObjectInputStream is = new BukkitObjectInputStream(new ByteArrayInputStream(contentBytes))) {
                read(is, kit);
            } catch (IOException | ClassNotFoundException e) {
                throw new JsonParseException(e);
            }

            return kit;
        }

        private static void write(BukkitObjectOutputStream os, Kit kit) throws IOException {
            os.writeInt(kit.getItems().size());
            for (var entry : kit.getItems().entrySet()) {
                os.writeInt(entry.getKey());
                os.writeObject(entry.getValue());
            }
        }

        private static void read(BukkitObjectInputStream is, Kit kit) throws IOException, ClassNotFoundException {
            int size = is.readInt();
            for (int i = 0; i < size; i++) {
                int slot = is.readInt();
                ItemStack stack = (ItemStack) is.readObject();
                kit.getItems().put(slot, stack);
            }
        }
    }
}
