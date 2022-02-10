package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private MatheChallenge plugin;

    public PlayerLeaveListener(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(MatheChallenge.PREFIX + "§a" + player.getName() + " §7hat das Spiel verlassen.");
        plugin.getPlayers().remove(player);

    }

}
