package com.prixix.mathechallenge.listeners;

import com.prixix.mathechallenge.MatheChallenge;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

        event.setJoinMessage(MatheChallenge.PREFIX + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " hat das Spiel betreten.");
        plugin.getPlayers().add(player);

        player.setGameMode(GameMode.SURVIVAL);

        player.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Willkommen zur MatheChallenge!");
        player.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Es kommt nach zufälliger Zeit eine neue Mathe-Aufgabe vor, die ein zufälliger Spieler lösen muss. Sollte die Aufgabe richtig beantwortet werden, passiert nichts, wenn sie gar nicht beantwortet wird, dann werden die Herzen aller Spieler auf 0.5 gesetzt. Sollte man die Frage falsch beantworten wird die HP auf eine zufällige Zahl gesetzt. Falls es Zahlen sind, die Nachkommastellen haben, dann werden 2 Nachkommastellen angegeben (z.B. bei 1 / 4 = 0.25).");
    }
}
