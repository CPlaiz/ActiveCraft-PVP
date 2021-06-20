package de.cplaiz.activecraft.Varo.Command;

import com.destroystokyo.paper.Title;
import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.Varo.Listener.KickTimer;
import de.cplaiz.activecraft.utils.Config;
import de.cplaiz.activecraft.utils.FileConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StartCommand2 extends KickTimer implements CommandExecutor {

    int time = Main.getPlugin().getConfig().getInt("TimerTime");
    String commandTimerEnd = Main.getPlugin().getConfig().getString("CommandExecutedOnTimerEnd");
    String commandEventStart = Main.getPlugin().getConfig().getString("CommandExecutedOnEventStart");
    int TimeBeforeEventStart = Main.getPlugin().getConfig().getInt("TimeBeforeEventStart");
    String worldName = Main.getPlugin().getConfig().getString("WorldName");
    String messageOnTimerStart = Main.getPlugin().getConfig().getString("MessageOnTimerStart");
    Config playerdataconfig;

    public static HashMap<UUID, Integer> timerActive = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Player> playerStoring = new HashMap<UUID, Player>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("event.start")) {

            BukkitRunnable runnableStart = new BukkitRunnable() {
                int zeitStart = TimeBeforeEventStart;

                Player p;


                @Override
                public void run() {

                    if (zeitStart == 0) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandEventStart);
                        cancel();

                        for (Player player : Main.getPlugin().getServer().getOnlinePlayers()) {

                        }

                        return;
                    }

                    for (Player player : Main.getPlugin().getServer().getOnlinePlayers()) {
                        Title title = new Title(ChatColor.RED + shortInteger(zeitStart));
                        player.sendTitle(title);
                    }

                    zeitStart--;

                }
            };
            runnableStart.runTaskTimer(Main.getPlugin(), 0, 20);


        }
        return true;
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
