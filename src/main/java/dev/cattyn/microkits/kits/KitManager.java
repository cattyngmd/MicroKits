package dev.cattyn.microkits.kits;

import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.utils.KitStorageUtil;

import java.util.*;

public class KitManager {
    public static final KitManager INSTANCE = new KitManager();

    private final Map<UUID, List<Kit>> localKits = new HashMap<>();

    private KitManager() {
    }

    public List<Kit> get(UUID uuid) {
        return localKits.getOrDefault(uuid, Collections.emptyList());
    }

    public Kit get(UUID id, String name) {
        List<Kit> kit = localKits.get(id);
        if (kit == null) return null;
        return kit.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean remove(UUID id, String name) {
        List<Kit> kits = localKits.computeIfAbsent(id, uuid -> new ArrayList<>());
        return kits.removeIf(local -> local.getName().equalsIgnoreCase(name));
    }

    public boolean save(UUID id, Kit kit) {
        List<Kit> kits = localKits.computeIfAbsent(id, uuid -> new ArrayList<>());
        kits.removeIf(local -> local.getName().equalsIgnoreCase(kit.getName()));
        return kits.add(kit);
    }

    // data
    public void saveData(UUID uuid) {
        KitStorageUtil.save(uuid);
        localKits.remove(uuid);
    }

    public void loadData(UUID uuid) {
        List<Kit> kits = KitStorageUtil.load(uuid);
        if (kits.isEmpty()) return;
        localKits.put(uuid, kits);
    }
}
