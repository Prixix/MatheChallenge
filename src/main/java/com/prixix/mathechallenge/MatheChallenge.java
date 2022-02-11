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
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MatheChallenge extends JavaPlugin {

    @Getter
    private static MatheChallenge instance;
    @Getter
    private Timer timer;
    @Getter
    private ArrayList<Player> players;
    @Getter
    private ConsoleCommandSender console;
    @Getter @Setter
    private Player currentPlayer;
    @Getter @Setter
    private Problem currentProblem;
    @Getter
    private int currentTaskId;
    @Getter
    private BukkitTask currentTask;

    private PluginManager pluginManager;

    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "MatheChallenge" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        console = Bukkit.getConsoleSender();
        pluginManager = this.getServer().getPluginManager();
        timer = new Timer(Timer.State.STOPPED, 0L);
        players = new ArrayList<>();

        registerCommands();
        registerListeners();

        console.sendMessage(PREFIX + "Das Plugin wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        console.sendMessage(PREFIX + "Das Plugin wurde deaktiviert!");
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
            player.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + deadPlayer.getName() + " ist gestorben!");
            player.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Die Challenge wurde beendet!");
            player.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Ihr habt " + ChatColor.YELLOW + getTimer().getFormattedTime(getTimer().getTime()) + ChatColor.GREEN + " Minuten gebraucht!");
            player.sendMessage("");
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    public void runChallenge() {
        long randomTime = new Random().nextInt(300 - 30 + 1) + 30;

        currentTask = Bukkit.getScheduler().runTaskLater(this, () -> {
            if (timer.getState() == Timer.State.RUNNING && getPlayers().size() > 0) {
                setCurrentPlayer(players.get(new Random().nextInt(players.size())));
                seconds = 10;
                startChallengeTimer();
                Problem problem = Problem.generateRandomProblem();
                console.sendMessage(problem.getOperation().operationResult(problem.getFirstNumber(), problem.getSecondNumber()) + " = ?");
                currentPlayer.sendMessage(PREFIX + ChatColor.RED + ChatColor.BOLD + "Mathematisches Problem: " + ChatColor.GOLD + problem.getFirstNumber() + " " + problem.getOperation().getOperationSymbol() + " " + problem.getSecondNumber() + " = ?");

                setCurrentProblem(problem);

                for(Player p : players) {
                    p.sendTitle(ChatColor.GREEN + currentPlayer.getName() + ChatColor.GRAY + " muss ein Problem l\\u00F6sen!", "", 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                }
            }
        }, randomTime * 20L);
    }

    int seconds = 10;
    public void startChallengeTimer() {
        currentTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (seconds > 0) {
                for(Player p : players) {
                    p.sendTitle(ChatColor.GREEN + "" + seconds + ChatColor.GRAY + " Sekunden verbleiben!", "", 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
                }
            } else {
                for(Player p : players) {
                    p.setHealth(1);
                    p.sendTitle(ChatColor.RED + "Das Problem wurde nicht gel\\u00F6st!", "", 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1F, 1F);
                }
                runChallenge();
                Bukkit.getScheduler().cancelTask(currentTaskId);
            }
            seconds--;
        }, 0L, 20L);
        seconds = 10;
    }
}
