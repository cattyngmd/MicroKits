package dev.cattyn.microkits.kit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.cattyn.microkits.api.kit.Kit;
import dev.cattyn.microkits.utils.SerializationUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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
            JsonArray content = new JsonArray();
            object.addProperty("name", kit.getName());
            for (var entry : kit.getItems().entrySet()) {
                JsonObject part = new JsonObject();
                part.addProperty("slot", entry.getKey());
                part.add("stack", SerializationUtil.serialize(entry.getValue()));
                content.add(part);
            }

            object.add("content", content);

            return object;
        }

        public Kit deserialize(JsonElement jsonElement) {
            if (!jsonElement.isJsonObject())
                throw new JsonParseException("Not a json object.");

            JsonObject object = jsonElement.getAsJsonObject();

            if (!object.has("name") || !object.has("content"))
                throw new JsonParseException("Invalid json object.");

            String name = object.get("name").getAsString();
            PlayerKit kit = new PlayerKit(name);
            for (JsonElement element : object.getAsJsonArray("content")) {
                if (!element.isJsonObject())
                    continue;

                JsonObject part = element.getAsJsonObject();
                if (!part.has("slot") || !part.has("stack"))
                    continue;

                int slot = part.get("slot").getAsInt();
                try {
                    ItemStack stack = SerializationUtil.deserialize(part.get("stack"));
                    kit.getItems().put(slot, stack);
                } catch (Throwable e) {
                    throw new JsonParseException("Invalid content.");
                }

            }

            return kit;
        }
    }
}
