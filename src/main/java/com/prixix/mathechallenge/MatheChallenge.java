package com.prixix.mathechallenge;

import com.prixix.mathechallenge.commands.RestartCommand;
import com.prixix.mathechallenge.commands.TimerCommand;
import com.prixix.mathechallenge.listeners.ChatListener;
import com.prixix.mathechallenge.listeners.EntityDeathListener;
import com.prixix.mathechallenge.listeners.PlayerJoinListener;
import com.prixix.mathechallenge.listeners.PlayerLeaveListener;
import com.prixix.mathechallenge.math.Problem;
import com.prixix.mathechallenge.timer.Timer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MatheChallenge extends JavaPlugin {

    @Getter
    private static MatheChallenge instance;
    @Getter
    private Logger logger;
    @Getter
    private Timer timer;
    @Getter
    private ArrayList<Player> players;
    @Getter @Setter
    private Player currentPlayer;
    @Getter @Setter
    private Problem currentProblem;
    @Getter
    private int currentTaskId;

    private PluginManager pluginManager;

    public static final String PREFIX = "§8[§6MatheChallenge§8] §7";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = this.getLogger();
        pluginManager = this.getServer().getPluginManager();
        timer = new Timer(Timer.State.STOPPED, 0L);
        players = new ArrayList<>();

        registerCommands();
        registerListeners();

        logger.log(Level.INFO, PREFIX + "Das Plugin wurde geladen!");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, PREFIX + "Das Plugin wurde deaktiviert!");
    }

    private void registerCommands() {
        getCommand("restart").setExecutor(new RestartCommand(this));
        getCommand("timer").setExecutor(new TimerCommand(this));
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerLeaveListener(this), this);
        pluginManager.registerEvents(new EntityDeathListener(this), this);
        pluginManager.registerEvents(new ChatListener(this), this);
    }

    public void sendLostMessage(Player deadPlayer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("");
            player.sendMessage(MatheChallenge.PREFIX + "§c" + deadPlayer.getName() + " ist gestorben!");
            player.sendMessage(MatheChallenge.PREFIX + "§aDie Challenge wurde beendet!");
            player.sendMessage(MatheChallenge.PREFIX + "§aIhr habt §e" + getTimer().getFormattedTime(getTimer().getTime()) + " §aMinuten gebraucht!");
            player.sendMessage("");
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    public void runChallenge() {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (timer.getState() == Timer.State.RUNNING) {
                Player player = players.get((int) Math.abs(players.size() * Math.random()));
                seconds = 10;
                startChallengeTimer(player);
                Problem problem = Problem.generateRandomProblem();
                player.sendMessage(PREFIX + "§c§lMathematisches Problem: §6" + problem.getFirstNumber() + " " + problem.getOperation().getOperationSymbol() + " " + problem.getSecondNumber() + " = ?");

                currentPlayer = player;
                currentProblem = problem;

                for(Player p : players) {
                    p.sendTitle("§a" + player.getName() + " §7muss ein Problem lösen!", "", 10, 20, 10);
                }
            }
        }, (long) (Math.random() * (3 * 60 * 20)));
    }

    int seconds = 10;
    public void startChallengeTimer(Player player) {
        currentTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (seconds > 0) {
                player.sendTitle("§a" + seconds + " §7Sekunden verbleiben!", "", 10, 20, 10);
            } else {
                sendLostMessage(player);
                for(Player p : players) {
                    p.setHealth(0.0);
                }
            }
            seconds--;
        }, 0L, 20L);
    }
}
