package dev.cattyn.microkits.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cattyn.microkits.MicroKits;
import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.kits.KitManagerImpl;
import dev.cattyn.microkits.kits.PlayerKit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class KitStorageUtil {
    private static final PlayerKit.Serializer serializer = new PlayerKit.Serializer();

    public static void save(UUID uuid) {
        Path path = MicroKits.getKitsPath().resolve(uuid.toString() + ".json");
        List<Kit> kits = KitManagerImpl.INSTANCE.get(uuid);
        save(path, kits);
    }

    public static void save(Path path, List<Kit> kits) {
        if (kits.isEmpty()) return;
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (Kit kit : kits) {
            array.add(serializer.serialize(kit));
        }
        object.add("kits", array);

        try {
            Files.writeString(path, object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Kit> load(UUID uuid) {
        Path path = MicroKits.getKitsPath().resolve(uuid.toString() + ".json");
        return load(path);
    }

    public static List<Kit> load(Path path) {
        try {
            String s = Files.readString(path);
            JsonObject object = JsonParser.parseString(s).getAsJsonObject();
            if (!object.has("kits")) return Collections.emptyList();
            List<Kit> kits = new ArrayList<>();
            for (JsonElement element : object.getAsJsonArray("kits")) {
                Kit kit = serializer.deserialize(element);
                kits.add(kit);
            }
            return kits;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
