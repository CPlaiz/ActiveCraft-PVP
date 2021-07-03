package de.cplaiz.activecraft.Varo.Listener;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
                int prevKills = fileConfig.getInt("stats.killed.players");

                //FileConfig playerlist = new FileConfig("playerdata/" + "playerlist" + ".yml");

                //List<String> Players = playerlist.getStringList("");

                fileConfig.set("stats.killed.players", prevKills + 1);
                fileConfig.saveConfig();
            }
        }
    }

    @EventHandler
    public void onAnimalDeath(EntityDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead.getKiller() instanceof Player) {
            Player p = dead.getKiller();
            if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {
                if (dead instanceof Animals || dead instanceof WaterMob && p != null) {
                    FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
                    fileConfig.set("stats.killed.animals", fileConfig.getInt("stats.killed.animals") + 1);
                    fileConfig.saveConfig();

                } else if (dead instanceof Monster || dead instanceof Flying || dead instanceof Slime && p != null) {
                    FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
                    fileConfig.set("stats.killed.monsters", fileConfig.getInt("stats.killed.animals") + 1);
                    fileConfig.saveConfig();
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Block block = event.getBlock();

        if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {

            FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
            fileConfig.set("stats.blocks.broken", fileConfig.getInt("stats.blocks.broken") + 1);
            fileConfig.saveConfig();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Block block = event.getBlock();

        if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {

            FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
            fileConfig.set("stats.blocks.placed", fileConfig.getInt("stats.blocks.placed") + 1);
            fileConfig.saveConfig();
        }
    }
}

