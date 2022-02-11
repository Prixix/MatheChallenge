package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import com.prixix.mathechallenge.math.Operation;
import com.prixix.mathechallenge.math.Problem;
import com.prixix.mathechallenge.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Random;

public class ChatListener implements Listener {

    private final MatheChallenge plugin;

    public ChatListener(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(plugin.getTimer().getState() == Timer.State.RUNNING && plugin.getCurrentPlayer() != null) {
            if(player.getUniqueId().equals(plugin.getCurrentPlayer().getUniqueId())) {
                Problem problem = plugin.getCurrentProblem();
                Operation operation = problem.getOperation();

                double input = Double.parseDouble(message);
                double result = Math.round(operation.operationResult(problem.getFirstNumber(), problem.getSecondNumber()) * 100.0) / 100.0;

                if(result == input) {
                    player.sendTitle(ChatColor.GREEN + "Richtig!", "", 0, 20, 20);
                    for(Player p : plugin.getServer().getOnlinePlayers()) {
                        p.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " hat eine richtige Antwort gegeben!");
                        p.sendMessage(MatheChallenge.PREFIX + ChatColor.GRAY + "Das Problem war: " + ChatColor.GREEN + problem.getFirstNumber() + " " + operation.getOperationSymbol() + " " + problem.getSecondNumber() + ChatColor.GRAY + " = " + ChatColor.GREEN + result);
                    }
                } else {
                    player.sendTitle(ChatColor.RED + "Falsch!", "", 0, 20, 20);
                    int newHealth = new Random().nextInt(10) + 1;
                    for(Player p : plugin.getServer().getOnlinePlayers()) {
                        p.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + player.getName() + " hat die Aufgabe falsch gel\\u00F6st!");
                        p.sendMessage(MatheChallenge.PREFIX + ChatColor.GRAY + "Das Problem war: " + ChatColor.RED + problem.getFirstNumber() + " " + operation.getOperationSymbol() + " " + problem.getSecondNumber() + ChatColor.GRAY + " = " + ChatColor.RED + result);
                        p.sendMessage(MatheChallenge.PREFIX + ChatColor.GRAY + "Die Herzen werden auf " + ChatColor.RED + newHealth + ChatColor.GRAY + " gesetzt!");
                        p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 1);
                        p.setHealth(newHealth);
                    }
                }
                Bukkit.getScheduler().cancelTask(plugin.getCurrentTaskId());
                plugin.setCurrentPlayer(null);
                plugin.runChallenge();
                event.setCancelled(true);
            }
        }
    }
}
