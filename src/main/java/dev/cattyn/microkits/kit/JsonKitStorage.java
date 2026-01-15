package dev.cattyn.microkits.kit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cattyn.microkits.MicroKits;
import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.api.KitStorage;
import dev.cattyn.microkits.utils.Globals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonKitStorage implements KitStorage {
    private static final PlayerKit.Serializer serializer = new PlayerKit.Serializer();

    private final Path root;

    public JsonKitStorage(Path root) {
        this.root = root;
    }

    @Override
    public void save(UUID uuid, List<Kit> kits) {
        if (kits.isEmpty()) return;
        Path path = root.resolve(uuid.toString() + ".json");

        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (Kit kit : kits) {
            array.add(serializer.serialize(kit));
        }
        object.add("kits", array);

        try {
            Files.writeString(path, Globals.GSON.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<Kit>> load(UUID uuid) {
        Path path = MicroKits.getKitsPath().resolve(uuid.toString() + ".json");

        try {
            String s = Files.readString(path);
            JsonObject object = JsonParser.parseString(s).getAsJsonObject();
            if (!object.has("kits")) return Optional.empty();

            List<Kit> kits = new ArrayList<>();
            for (JsonElement element : object.getAsJsonArray("kits"))
                kits.add(serializer.deserialize(element));

            if (kits.isEmpty()) return Optional.empty();
            return Optional.of(kits);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
