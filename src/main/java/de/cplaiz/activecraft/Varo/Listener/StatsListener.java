package de.cplaiz.activecraft.Varo.Listener;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {

    String worldName = Main.getPlugin().getConfig().getString("WorldName");

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        Player p = event.getEntity();
        Entity killer = p.getKiller();

        //System.out.println(killer.getName());
        //System.out.println("loggy log log + " + p.getName());

        if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {

            if (killer instanceof Player) {
                FileConfig fileConfig = new FileConfig("playerdata/" + killer.getUniqueId().toString() + ".yml");
                int prevKills = fileConfig.getInt("stats.kills");

                //FileConfig playerlist = new FileConfig("playerdata/" + "playerlist" + ".yml");

                //List<String> Players = playerlist.getStringList("");

                fileConfig.set("stats.kills", prevKills + 1);
                fileConfig.saveConfig();
            }
        }
    }

}

