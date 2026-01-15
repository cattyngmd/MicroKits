package dev.cattyn.microkits.kit;

import dev.cattyn.microkits.MicroKits;
import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.api.KitStorage;
import dev.cattyn.microkits.api.KitManager;

import java.util.*;

public class KitManagerImpl implements KitManager {
    public static final KitManagerImpl INSTANCE = new KitManagerImpl();

    private final Map<UUID, List<Kit>> localKits = new HashMap<>();
    private final KitStorage storage = new JsonKitStorage(MicroKits.getKitsPath());

    private KitManagerImpl() {
    }

    @Override
    public List<Kit> get(UUID uuid) {
        return localKits.getOrDefault(uuid, Collections.emptyList());
    }

    @Override
    public Kit get(UUID id, String name) {
        List<Kit> kit = localKits.get(id);
        if (kit == null) return null;
        return kit.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public boolean remove(UUID id, String name) {
        List<Kit> kits = localKits.computeIfAbsent(id, uuid -> new ArrayList<>());
        boolean bl = kits.removeIf(local -> local.getName().equalsIgnoreCase(name));
        storage.save(id, kits);
        return bl;
    }

    @Override
    public boolean save(UUID id, Kit kit) {
        List<Kit> kits = localKits.computeIfAbsent(id, uuid -> new ArrayList<>());
        kits.removeIf(local -> local.getName().equalsIgnoreCase(kit.getName()));
        kits.add(kit);
        storage.save(id, kits);
        return true;
    }

    @Override
    public KitStorage getStorage() {
        return storage;
    }

    public void loadPlayer(UUID uuid) {
        storage.load(uuid).ifPresent(kits -> localKits.put(uuid, kits));
    }

    public void savePlayer(UUID uuid) {
        storage.save(uuid, get(uuid));
        localKits.remove(uuid);
    }
}
