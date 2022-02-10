package com.prixix.mathechallenge.commands;

import com.prixix.mathechallenge.MatheChallenge;
import com.prixix.mathechallenge.timer.Timer;
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
            sender.sendMessage(MatheChallenge.prefix + "§cDu hast keine Rechte dafür!");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(MatheChallenge.prefix + "§cBitte nutze§7: §9/timer <start|pause|reset|resume>");
        }

        switch (args[0]) {
            case "start" -> {
                if (timer.getState() == Timer.State.RUNNING) {
                    sender.sendMessage(MatheChallenge.prefix + "§cDer Timer läuft bereits!");
                    break;
                }
                timer.setState(Timer.State.RUNNING);
                sender.sendMessage(MatheChallenge.prefix + "§aDer Timer wurde gestartet!");
            }
            case "stop", "pause" -> {
                if (timer.getState() == Timer.State.STOPPED) {
                    sender.sendMessage(MatheChallenge.prefix + "§cDer Timer ist bereits gestoppt!");
                    break;
                }
                timer.setState(Timer.State.STOPPED);
                sender.sendMessage(MatheChallenge.prefix + "§aDer Timer wurde gestoppt!");
            }
            case "resume" -> {
                if (timer.getState() == Timer.State.RUNNING) {
                    sender.sendMessage(MatheChallenge.prefix + "§cDer Timer läuft bereits!");
                    break;
                }
                timer.setState(Timer.State.RUNNING);
                sender.sendMessage(MatheChallenge.prefix + "§aDer Timer wurde fortgesetzt!");
            }
            case "reset" -> {
                timer.setTime(0);
                sender.sendMessage(MatheChallenge.prefix + "§aDer Timer wurde zurückgesetzt!");
            }
            default -> sender.sendMessage(MatheChallenge.prefix + "§cBitte nutze§7: §9/timer <start|pause|reset|resume>");
        }

        return false;
    }
}
