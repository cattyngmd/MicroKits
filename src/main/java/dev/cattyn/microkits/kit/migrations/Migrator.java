package dev.cattyn.microkits.kit.migrations;

import com.google.gson.JsonObject;

public interface Migrator {
    int getInitialVersion();

    JsonObject migrate(JsonObject object);
}
