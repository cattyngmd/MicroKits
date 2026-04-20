package dev.cattyn.microkits.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface Globals {
    Gson GSON = new GsonBuilder()
            .setLenient()
            .create();
}
