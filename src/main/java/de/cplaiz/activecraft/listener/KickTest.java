package de.cplaiz.activecraft.listener;

import de.cplaiz.activecraft.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class KickTest implements Listener {




    HashMap<String, Integer> times = new HashMap<String, Integer>();

    String worldName = Main.getPlugin().getConfig().getString("WorldName");
    int time = Main.getPlugin().getConfig().getInt("TimerTime");

    public void onEnable() {

    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event, Player player) {

        String uuid = player.getUniqueId().toString();
        if (times.containsKey(uuid)) {
            final int  hashtime = times.get(uuid);



        }
    }


    @EventHandler
        public void OnPlayerBetreten(PlayerJoinEvent event) {

            Player player = event.getPlayer();

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
                        player.performCommand("spawn");
                        cancel();
                        return;
                    }

                    //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(shortInteger(time)));
                    //hashtime--;

                    if (!player.isOnline()) {
                        //times.put(uuid, hashtime);
                    }
                }
            };

        }



        //while(player.getWorld().getName().equals(worldName)) {




        //}
    }


