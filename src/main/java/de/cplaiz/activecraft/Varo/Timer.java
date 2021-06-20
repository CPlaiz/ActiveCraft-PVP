package de.cplaiz.activecraft.Varo;

import de.cplaiz.activecraft.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    private  boolean running;
    private int time;

    public Timer(boolean running , int time) {
        this.running = running;
        this.time = time;

        //run();

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if(!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR , new TextComponent(ChatColor.AQUA.toString() +
                        "Timer ist pausiert"));
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR , new TextComponent(ChatColor.AQUA.toString() +
                    ChatColor.BOLD + getTime()));

        }
    }
    
    public void run(){
        new BukkitRunnable() {
            @Override
            public void run() {

                sendActionBar();

                if(!isRunning()) {
                    return;
                }

                setTime(getTime() - 1);

               // if(setTime(getTime() = 0));

            }
        }.runTaskTimer(Main.INSTANCE , 20 , 20);
    }

    public String shortInteger(int duration) {
        String string = "";
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if (duration / 60 / 60 / 24 >= 1) {
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1)
            seconds = duration;
        if (hours <= 9) {
            string = String.valueOf(string) + "0" + hours + ":";
        } else {
            string = String.valueOf(string) + hours + ":";
        }
        if (minutes <= 9) {
            string = String.valueOf(string) + "0" + minutes + ":";
        } else {
            string = String.valueOf(string) + minutes + ":";
        }
        if (seconds <= 9) {
            string = String.valueOf(string) + "0" + seconds;
        } else {
            string = String.valueOf(string) + seconds;
        }
        return string;
    }
}
