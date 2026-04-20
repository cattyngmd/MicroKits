package dev.cattyn.microkits.kit.migrations;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.cattyn.microkits.api.kit.Kit;
import dev.cattyn.microkits.kit.PlayerKit;
import dev.cattyn.microkits.utils.SerializationUtil;
import org.bukkit.inventory.ItemStack;

public class MigratorV1ToV2 implements Migrator {
    private static final PlayerKit.Serializer serializer = new PlayerKit.Serializer();

    @Override
    public int getInitialVersion() {
        return 1;
    }

    @Override
    public JsonObject migrate(JsonObject object) {
        JsonArray newKits = new JsonArray();
        JsonArray kits = object.getAsJsonArray("kits");
        for (JsonElement element : kits) {
            try {
                newKits.add(serializer.serialize(deserialize(element)));
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
        }
        object.add("kits", newKits);
        return object;
    }

    private Kit deserialize(JsonElement jsonElement) {
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
