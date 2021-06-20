package de.cplaiz.activecraft.listener;

import de.cplaiz.activecraft.Main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class KickScheduler implements Listener {


    HashMap<String, Integer> times = new HashMap<String, Integer>();

    //private final String command = Tutorial.getPlugin().getMainConfig().toFileConfiguration().getString("CommandExecutedOnTimerEnd");
    //private int time = Tutorial.getPlugin().getMainConfig().toFileConfiguration().getInt("TimerTime");


    String command = Main.getPlugin().getConfig().getString("CommandExecutedOnTimerEnd");
    String worldName = Main.getPlugin().getConfig().getString("WorldName");
    boolean TimerActive = Main.getPlugin().getConfig().getBoolean("TimerIsActive");
    int time = Main.getPlugin().getConfig().getInt("TimerTime");

    public void onEnable() {

    }


    //stats = new YamlConfiguration();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString();








        System.out.println(command + " " + time);

        System.out.println("World Name: " + player.getWorld().getName());
        System.out.println("World Name aus config: " + worldName);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                switch (time) {
                    case 30:
                        player.sendMessage("§cDu wirst in 30 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 10:
                        player.sendMessage("§cDu wirst in 10 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 5:
                        player.sendMessage("§cDu wirst in 5 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 4:
                        player.sendMessage("§cDu wirst in 4 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 3:
                        player.sendMessage("§cDu wirst in 3 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 2:
                        player.sendMessage("§cDu wirst in 2 Sekunden vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 1:
                        player.sendMessage("§cDu wirst in 1 Sekunde vom Server gekickt!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;

                }

                if (time == 0) {
                    player.performCommand(command);
                    cancel();
                    return;
                }

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(shortInteger(time)));
                time--;

                if (!player.isOnline()) {
                    times.put(uuid, time);
                }
            }
        };
        //if(TimerActive) {
        if (player.getWorld().getName().equals(worldName)) {

            runnable.runTaskTimer(Main.getPlugin(), 0, 20);
        }
        //}

    }
    //}
    //}



    @EventHandler
    public void onPlayerWorldChange(PlayerTeleportEvent event, Player player) {

        String uuid = player.getUniqueId().toString();

        //if (to.getWorld().getName().equals(worldName)) {

            System.out.println("2 - " + command + " " + time);




            System.out.println("World Name: " + player.getWorld().getName());
            System.out.println("World Name aus config: " + worldName);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {



                    switch (time) {
                        case 30:
                            player.sendMessage("§cDu wirst in 30 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 10:
                            player.sendMessage("§cDu wirst in 10 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 5:
                            player.sendMessage("§cDu wirst in 5 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 4:
                            player.sendMessage("§cDu wirst in 4 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 3:
                            player.sendMessage("§cDu wirst in 3 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 2:
                            player.sendMessage("§cDu wirst in 2 Sekunden vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;
                        case 1:
                            player.sendMessage("§cDu wirst in 1 Sekunde vom Server gekickt!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            break;

                    }

                    if (time == 0) {
                        player.performCommand(command);
                        cancel();
                        return;
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(shortInteger(time)));
                    time--;
                    System.out.println(time);


                }

            };

            //if (from.getWorld().getName().equals(worldName)) {
                times.put(uuid, time);
                runnable.cancel();
            //}



        //}


    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event, Player player) {

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
