package com.prixix.mathechallenge.commands;

import com.prixix.mathechallenge.MatheChallenge;
import com.prixix.mathechallenge.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {

    private final MatheChallenge plugin;
    private final Timer timer;

    public TimerCommand(MatheChallenge plugin) {
        this.plugin = plugin;
        this.timer = plugin.getTimer();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("mathechallenge.timer")) {
            sender.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + "Du hast keine Rechte dafür!");
            return true;
        }

        if(args.length == 0) {
            //sender.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + "Bitte nutze" + ChatColor.GRAY + ": " + ChatColor.GOLD + "/timer <start|pause|reset|resume>");
            return false;
        }

        switch (args[0]) {
            case "start" -> {
                if (timer.getState() == Timer.State.RUNNING) {
                    sender.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + "Der Timer läuft bereits!");
                    break;
                }
                plugin.runChallenge();
                timer.setState(Timer.State.RUNNING);
                sender.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Der Timer wurde gestartet!");
            }
            case "stop", "pause" -> {
                if (timer.getState() == Timer.State.PAUSED) {
                    sender.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Der Timer ist bereits gestoppt!");
                    break;
                }
                timer.setState(Timer.State.PAUSED);
                sender.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Der Timer wurde gestoppt!");
                plugin.getCurrentTask().cancel();
            }
            case "resume" -> {
                if (timer.getState() == Timer.State.RUNNING) {
                    sender.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + "Der Timer läuft bereits!");
                    plugin.runChallenge();
                    break;
                }
                timer.setState(Timer.State.RUNNING);
                sender.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Der Timer wurde fortgesetzt!");
            }
            case "reset" -> {
                timer.setTime(0);
                sender.sendMessage(MatheChallenge.PREFIX + ChatColor.GREEN + "Der Timer wurde zurückgesetzt!");
                break;
            }
            default -> sender.sendMessage(MatheChallenge.PREFIX + ChatColor.RED + "Bitte nutze" + ChatColor.GRAY + ": " + ChatColor.GOLD + "/timer <start|pause|reset|resume>");
        }

        return true;
    }
}
