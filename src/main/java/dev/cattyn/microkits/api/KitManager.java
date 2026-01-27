package dev.cattyn.microkits.api;

import java.util.List;
import java.util.UUID;

public interface KitManager {
    List<Kit> get(UUID owner);

    Kit get(UUID owner, String name);

    boolean save(UUID owner, Kit kit);

    boolean remove(UUID owner, String name);

    boolean isOnCooldown(UUID owner, int timeMs);

    KitStorage getStorage();
}
