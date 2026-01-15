package dev.cattyn.microkits.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KitStorage {
    void save(UUID uuid, List<Kit> kits);
    Optional<List<Kit>> load(UUID uuid);
}
