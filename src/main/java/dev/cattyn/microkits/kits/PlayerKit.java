package dev.cattyn.microkits.kits;

import com.google.gson.*;
import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.utils.SerializationUtil;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.lang.reflect.Type;
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

    public static class Serializer implements JsonSerializer<Kit>, JsonDeserializer<Kit> {
        @Override
        public JsonElement serialize(Kit kit, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject object = new JsonObject();
            JsonArray content = new JsonArray();
            object.addProperty("name", kit.getName());
            object.add("content", content);
            for (var entry : kit.getItems().entrySet()) {
                JsonObject part = new JsonObject();
                part.addProperty("slot", entry.getKey());
                part.addProperty("stack", SerializationUtil.serialize(entry.getValue()));
            }

            return null;
        }

        @Override
        public Kit deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject())
                throw new JsonParseException("Not a json object.");

            JsonObject object = jsonElement.getAsJsonObject();

            if (!object.has("name") || !object.has("content"))
                throw new JsonParseException("Invalid json object.");

            String name = object.get("name").getAsString();
            PlayerKit kit = new PlayerKit(name);
            for (JsonElement element : object.getAsJsonArray()) {
                if (!element.isJsonObject())
                    continue;

                if (!object.has("slot") || !object.has("stack"))
                    continue;

                int slot = object.get("slot").getAsInt();
                try {
                    ItemStack stack = SerializationUtil.deserialize(object.get("content").getAsString());
                    kit.getItems().put(slot, stack);
                } catch (IOException e) {
                    throw new JsonParseException("Invalid content.");
                }

            }

            return kit;
        }
    }
}
