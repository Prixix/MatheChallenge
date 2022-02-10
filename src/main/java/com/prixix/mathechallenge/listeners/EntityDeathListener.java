package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import com.prixix.mathechallenge.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private MatheChallenge plugin;

    public EntityDeathListener(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(plugin.getTimer().getState() == Timer.State.RUNNING) {
            if (event.getEntity().getType() == EntityType.ENDER_DRAGON) {
                plugin.getTimer().setState(Timer.State.PAUSED);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("");
                    player.sendMessage(MatheChallenge.PREFIX + "§cDer Enderdrache ist gestorben!");
                    player.sendMessage(MatheChallenge.PREFIX + "§aDie Challenge wurde beendet!");
                    player.sendMessage(MatheChallenge.PREFIX + "§aIhr habt §e" + plugin.getTimer().getFormattedTime(plugin.getTimer().getTime()) + " §aMinuten gebraucht!");
                    player.sendMessage("");
                }
                Bukkit.getScheduler().cancelTask(plugin.getCurrentTaskId());
                return;
            }
            if(event.getEntity().getType() == EntityType.PLAYER) {
                Player deadPlayer = (Player) event.getEntity();
                if(plugin.getTimer().getState() == Timer.State.RUNNING) {
                    plugin.getTimer().setState(Timer.State.PAUSED);
                    plugin.sendLostMessage(deadPlayer);
                    Bukkit.getScheduler().cancelTask(plugin.getCurrentTaskId());
                }
            }
        }
    }
}
