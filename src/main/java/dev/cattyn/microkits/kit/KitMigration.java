package dev.cattyn.microkits.kit;

import com.google.gson.JsonObject;
import dev.cattyn.microkits.kit.migrations.Migrator;

import java.util.Map;
import java.util.TreeMap;

public final class KitMigration {
    private final Map<Integer, Migrator> migrators = new TreeMap<>();

    KitMigration() {
    }

    public KitMigration register(Migrator migrator) {
        migrators.put(migrator.getInitialVersion(), migrator);
        return this;
    }

    public JsonObject migrate(JsonObject root) throws MigrationException {
        int version = root.has("v") ? root.get("v").getAsInt() : 1;
        while (version < KitStorageJson.KIT_FORMAT) {
            Migrator migrator = migrators.get(version);
            if (migrator == null)
                throw new IllegalStateException("No migrator for version " + version);

            try {
                root = migrator.migrate(root);
            } catch (Throwable t) {
                throw new MigrationException(t);
            }
            version++;
            root.addProperty("v", version);
        }

        return root;
    }

    public static class MigrationException extends Exception {
        public MigrationException(Throwable t) {
            super(t);
        }
    }
}
