package com.prixix.mathechallenge.commands;

import com.prixix.mathechallenge.MatheChallenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RestartCommand implements CommandExecutor {

    private MatheChallenge plugin;

    public RestartCommand(MatheChallenge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mathechallenge.restart")) {
            sender.sendMessage(MatheChallenge.prefix + "§cDu hast keine Rechte dafür!");
            return true;
        }

        plugin.getServer().shutdown();
        return false;
    }
}
