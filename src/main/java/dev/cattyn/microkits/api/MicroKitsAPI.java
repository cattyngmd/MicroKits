package dev.cattyn.microkits.api;

import java.util.Objects;

public class MicroKitsAPI {
    private static volatile MicroKitsProvider provider;

    public static void setProvider(MicroKitsProvider provider) {
        if (MicroKitsAPI.provider != null) {
            throw new IllegalStateException("Provider already set!");
        }
        MicroKitsAPI.provider = Objects.requireNonNull(provider, "provider");
    }

    public static MicroKitsProvider getProvider() {
        if (provider == null) {
            throw new IllegalStateException("MicroKits not initialized!");
        }
        return provider;
    }

    public static boolean isInitialized() {
        return provider != null;
    }
}
