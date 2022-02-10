package com.prixix.mathechallenge.timer;

import com.prixix.mathechallenge.MatheChallenge;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Timer {

    @Getter @Setter
    private State state;
    @Getter @Setter
    private long time;

    public Timer(State state, long time) {
        this.state = state;
        this.time = time;
        run();
    }

    public void sendActionBar() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(state == State.STOPPED) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4§lDer Timer ist gestoppt!"));
                return;
            }
            if(state == State.PAUSED) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§lDer Timer ist pausiert!"));
                return;
            }
            if(state == State.RUNNING) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6§l" + getFormattedTime(getTime())));
                return;
            }
        }
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimer(MatheChallenge.getInstance(), () -> {
            sendActionBar();

            if(getState() == State.RUNNING) {
                setTime(getTime() + 1);
            }
        }, 0, 20);
    }

    private String getFormattedTime(long seconds) {
        long minutes = seconds / 60;
        long secondsLeft = seconds % 60;

        String formattedTime = "";

        if(minutes < 10) {
            formattedTime += "0";
        }
        formattedTime += minutes + ":";

        if(secondsLeft < 10) {
            formattedTime += "0";
        }
        formattedTime += secondsLeft;

        return formattedTime;
    }

    public enum State {
        RUNNING, PAUSED, STOPPED
    }
}
