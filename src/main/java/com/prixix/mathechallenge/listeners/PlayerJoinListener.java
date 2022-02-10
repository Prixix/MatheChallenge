package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private MatheChallenge plugin;

    public PlayerJoinListener(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(MatheChallenge.PREFIX + "§a" + player.getName() + " §7hat das Spiel betreten.");
        plugin.getPlayers().add(player);
    }
}
