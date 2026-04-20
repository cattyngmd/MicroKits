package dev.cattyn.microkits.kit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cattyn.microkits.api.kit.Kit;
import dev.cattyn.microkits.api.kit.KitStorage;
import dev.cattyn.microkits.kit.migrations.MigratorV1ToV2;
import dev.cattyn.microkits.utils.Globals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class KitStorageJson implements KitStorage {
    public static final int KIT_FORMAT = 2;

    private static final PlayerKit.Serializer serializer = new PlayerKit.Serializer();

    private static final KitMigration migrations = new KitMigration()
            .register(new MigratorV1ToV2());

    private final Path root;

    public KitStorageJson(Path root) {
        this.root = root;
    }

    @Override
    public void save(UUID uuid, List<Kit> kits) {
        if (kits.isEmpty()) return;
        Path path = root.resolve(uuid.toString() + ".json");

        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (Kit kit : kits) {
            try {
                array.add(serializer.serialize(kit));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        object.add("kits", array);
        object.addProperty("v", KIT_FORMAT);

        try {
            Files.writeString(path, Globals.GSON.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<Kit>> load(UUID uuid) {
        Path path = getPath(uuid);

        if (!path.toFile().exists())
            return Optional.empty();

        try {
            String s = Files.readString(path);
            JsonObject object = JsonParser.parseString(s).getAsJsonObject();

            object = migrations.migrate(object);

            if (!object.has("kits")) return Optional.empty();

            List<Kit> kits = new ArrayList<>();
            for (JsonElement element : object.getAsJsonArray("kits"))
                kits.add(serializer.deserialize(element));

            if (kits.isEmpty()) return Optional.empty();
            return Optional.of(kits);
        } catch (IOException | KitMigration.MigrationException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Path getPath(UUID uuid) {
        return root.resolve(uuid.toString() + ".json");
    }
}
