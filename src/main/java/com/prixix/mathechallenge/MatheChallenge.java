package com.prixix.mathechallenge;

import com.prixix.mathechallenge.commands.RestartCommand;
import com.prixix.mathechallenge.commands.TimerCommand;
import com.prixix.mathechallenge.listeners.PlayerJoinListener;
import com.prixix.mathechallenge.listeners.PlayerLeaveListener;
import com.prixix.mathechallenge.timer.Timer;
import lombok.Getter;
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

    private PluginManager pluginManager;

    public static final String PREFIX = "§8[§6MatheChallenge§8] §7";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = this.getLogger();
        pluginManager = this.getServer().getPluginManager();
        timer = new Timer(Timer.State.STOPPED, 0);
        players = new ArrayList<>();

        registerCommands();
        registerListeners();

        logger.log(Level.INFO, PREFIX + "Das Plugin wurde geladen!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.log(Level.INFO, PREFIX + "Das Plugin wurde deaktiviert!");
    }

    private void registerCommands() {
        getCommand("restart").setExecutor(new RestartCommand(this));
        getCommand("timer").setExecutor(new TimerCommand(this));
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerLeaveListener(this), this);
    }
}
