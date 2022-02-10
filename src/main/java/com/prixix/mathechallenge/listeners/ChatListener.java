package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import com.prixix.mathechallenge.math.Operation;
import com.prixix.mathechallenge.math.Problem;
import com.prixix.mathechallenge.timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final MatheChallenge plugin;

    public ChatListener(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(plugin.getTimer().getState() == Timer.State.RUNNING) {
            if(player.getUniqueId().equals(plugin.getCurrentPlayer().getUniqueId())) {
                Problem problem = plugin.getCurrentProblem();
                Operation operation = problem.getOperation();

                double input = Double.parseDouble(message);

                if(operation.operationResult(problem.getFirstNumber(), problem.getSecondNumber()) == input) {
                    player.sendMessage(MatheChallenge.PREFIX + "§aRichtig!");
                    for(Player p : plugin.getServer().getOnlinePlayers()) {
                        p.sendMessage(MatheChallenge.PREFIX + "§a" + player.getName() + " §7hat eine richtige Antwort gegeben!");
                    }
                    return;
                }

                double damage = Math.random() * 9.5 + 1;

                for(Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendMessage(MatheChallenge.PREFIX + "§c" + player.getName() + " hat die Aufgabe falsch gelöst!");
                    p.sendMessage(MatheChallenge.PREFIX + "§cDas Ergebnis war: " + operation.operationResult(problem.getFirstNumber(), problem.getSecondNumber()));
                    p.sendMessage(MatheChallenge.PREFIX + "§cJeder bekommt " + damage + " Herzen Schaden!");
                    p.damage(damage);
                }
            }
        }
    }
}
