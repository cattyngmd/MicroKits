package dev.cattyn.microkits.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class PlayerManager implements Listener {
    public static final PlayerManager INSTANCE = new PlayerManager();

    private final Set<UUID> selected = new HashSet<>();

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

    public boolean hasSelected(Player player) {
        return selected.contains(player.getUniqueId());
    }

    public void add(Player player) {
        selected.add(player.getUniqueId());
    }

    public void remove(Player player) {
        selected.remove(player.getUniqueId());
    }
}
