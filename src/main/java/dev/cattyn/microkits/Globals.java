package dev.cattyn.microkits;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.kits.PlayerKit;

public interface Globals {
    Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Kit.class, new PlayerKit.Serializer())
            .create();
}
