package dev.cattyn.microkits.players;

import dev.cattyn.microkits.api.Kit;
import dev.cattyn.microkits.api.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class PlayerManagerImpl implements PlayerManager, Listener {
    public static final PlayerManagerImpl INSTANCE = new PlayerManagerImpl();

    private final Map<UUID, Kit> selectedKits = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        remove(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        remove(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        remove(event.getEntity());
    }

    @Override
    public Kit getSelected(UUID player) {
        return selectedKits.get(player);
    }

    public void add(Player player, Kit kit) {
        selectedKits.put(player.getUniqueId(), kit);
    }

    public void remove(Player player) {
        selectedKits.remove(player.getUniqueId());
    }
}
