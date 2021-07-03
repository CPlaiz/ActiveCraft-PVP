package de.cplaiz.activecraft.Varo.Listener;

import com.destroystokyo.paper.Title;
import de.cplaiz.activecraft.Main;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;


import java.util.HashMap;
import java.util.UUID;

public class KickTimer implements Listener, CommandExecutor {

    int time = Main.getPlugin().getConfig().getInt("TimerTime");
    String commandTimerEnd = Main.getPlugin().getConfig().getString("CommandExecutedOnTimerEnd");
    //List<String> ptrue = Tutorial.getPlugin().getConfig().getStringList("PermissionsTrueOnTimerEnd");
    //List<String> pfalse = Tutorial.getPlugin().getConfig().getStringList("PermissionsFalseOnTimerEnd");
    String worldName = Main.getPlugin().getConfig().getString("WorldName");
    String messageOnTimerStart = Main.getPlugin().getConfig().getString("MessageOnTimerStart");
    String commandEventStart = Main.getPlugin().getConfig().getString("CommandExecutedOnEventStart");
    String StartMessage = Main.getPlugin().getConfig().getString("StartMessage");
    int TimeBeforeEventStart = Main.getPlugin().getConfig().getInt("TimeBeforeEventStart");
    Config playerdataconfig;

    public static HashMap<UUID, Integer> timerActive = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Player> playerStoring = new HashMap<UUID, Player>();
    //private HashMap<UUID, Integer> taskid = new HashMap<UUID, Integer>();

    //gson json krams




    @EventHandler
    public void onPlayerChangeWorld(PlayerTeleportEvent event) {

        Player player = event.getPlayer();

        //System.out.println(timerActive.get(player.getUniqueId()));
        //System.out.println(playerStoring.get(player.getUniqueId()));

        FileConfig fileConfig = new FileConfig("playerdata/" + player.getUniqueId().toString() + ".yml");
        FileConfig dataConfig = new FileConfig("data.yml");

        boolean onDuty = fileConfig.getBoolean("on-duty");

        Location to = event.getTo();
        Location from = event.getFrom();

        if (dataConfig.getBoolean("is-active")) {

            if (!onDuty) {

                if (!to.getWorld().getName().equals(from.getWorld().getName())) {

                    playerStoring.put(player.getUniqueId(), player);
                    if (!timerActive.containsKey(player.getUniqueId())) {
                        if (to.getWorld().getName().equals(worldName) || to.getWorld().getName().equals(worldName + "_nether")) {

                            player.sendMessage(messageOnTimerStart);

                            BukkitRunnable runnable = new BukkitRunnable() {

                                int zeit = time;

                                Player p;


                                @Override
                                public void run() {

                                    p = playerStoring.get(player.getUniqueId());

                                    switch (time) {
                                        case 600:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l10 Minuten§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 300:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l5 Minuten§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 60:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l1 Minute§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 30:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l30 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 10:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l10 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 5:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l5 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 4:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l4 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 3:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l3 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 2:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l2 Sekunden§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;
                                        case 1:
                                            p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l1 Sekunde§r §bgekickt!");
                                            p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                            break;

                                    }

                                    if (zeit == 0) {
                                        p.performCommand(commandTimerEnd);
                                        cancel();
                                        timerActive.remove(p.getUniqueId());
                                        int episodes = fileConfig.getInt("episodes");
                                        fileConfig.set("episodes", episodes + 1);
                                        fileConfig.saveConfig();

                                        return;
                                    }

                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(shortInteger(zeit)));


                                    zeit--;
                                    //System.out.println(zeit);
                                    timerActive.put(player.getUniqueId(), zeit);


                                }
                            };

                            runnable.runTaskTimer(Main.getPlugin(), 0, 20);
                            fileConfig.set("taskid", runnable.getTaskId());
                            fileConfig.saveConfig();
                            //System.out.println("TaskId saved");

                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        FileConfig dataConfig = new FileConfig("data.yml");

        if (!dataConfig.getBoolean("is-active")) {

            if (sender.hasPermission("event.start")) {

                BukkitRunnable runnableStart = new BukkitRunnable() {
                    int zeitStart = TimeBeforeEventStart;

                    Player p;


                    @Override
                    public void run() {

                        if (zeitStart == 0) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandEventStart);
                            cancel();

                            FileConfig dataConfig = new FileConfig("data.yml");
                            dataConfig.set("is-active", true);
                            dataConfig.saveConfig();

                            for (Player player : Main.getPlugin().getServer().getOnlinePlayers()) {

                                Title title = new Title(ChatColor.RED + StartMessage);
                                player.sendTitle(title);

                                FileConfig fileConfig = new FileConfig("playerdata/" + player.getUniqueId().toString() + ".yml");

                                playerStoring.put(player.getUniqueId(), player);
                                if (!timerActive.containsKey(player.getUniqueId())) {

                                    BukkitRunnable runnable = new BukkitRunnable() {

                                        int zeit = time;

                                        Player p;


                                        @Override
                                        public void run() {

                                            p = playerStoring.get(player.getUniqueId());

                                            switch (zeit) {
                                                case 600:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l10 Minuten§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 300:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l5 Minuten§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 60:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l1 Minute§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 30:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l30 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 10:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l10 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 5:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l5 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 4:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l4 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 3:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l3 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 2:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l2 Sekunden§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;
                                                case 1:
                                                    p.sendMessage("§4§lWARNUNG §bDu wirst in §a§l1 Sekunde§r §bgekickt!");
                                                    p.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                                    break;

                                            }

                                            if (zeit == 0) {
                                                p.performCommand(commandTimerEnd);
                                                cancel();
                                                timerActive.remove(p.getUniqueId());
                                                int episodes = fileConfig.getInt("episodes");
                                                fileConfig.set("episodes", episodes + 1);
                                                fileConfig.saveConfig();
                                                return;
                                            }

                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(shortInteger(zeit)));

                                            zeit--;
                                            timerActive.put(player.getUniqueId(), zeit);
                                        }
                                    };

                                    runnable.runTaskTimer(Main.getPlugin(), 0, 20);
                                    fileConfig.set("taskid", runnable.getTaskId());
                                    fileConfig.saveConfig();
                                }
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

    public HashMap<UUID, Integer> getTimerActive() {
        //System.out.println("1r");
        return timerActive;
    }

    public void setTimerActive(HashMap<UUID, Integer> timerActive) {
        //System.out.println("2r");
        this.timerActive = timerActive;
    }

    public HashMap<UUID, Player> getPlayerStoring() {
        //System.out.println("3r");
        return playerStoring;

    }

    public void setPlayerStoring(HashMap<UUID, Player> playerStoring) {
        //System.out.println("4r");
        this.playerStoring = playerStoring;
    }

}
