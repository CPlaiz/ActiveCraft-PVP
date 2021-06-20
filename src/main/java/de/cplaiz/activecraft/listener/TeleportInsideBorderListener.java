package de.cplaiz.activecraft.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class TeleportInsideBorderListener implements Listener {

    private HashMap<String, Double> values = new HashMap<String, Double>();


@EventHandler
    public void onJoin(PlayerJoinEvent event) {

    Player p = event.getPlayer();
    Location loc = p.getLocation();
    WorldBorder border = loc.getWorld().getWorldBorder();
    double radius = border.getSize() / 2;
    double radius2 = radius - radius*2;
    Location location = p.getLocation(), center = border.getCenter();
    int px = p.getLocation().getBlockX();
    int py = p.getLocation().getBlockY();
    int pz = p.getLocation().getBlockZ();

    if(px > radius) {
        values.put("x", radius);
    }
    if(pz > radius) {
        values.put("z", radius);
    }
    if(px < radius2) {
        values.put("x", radius2);
    }
    if(pz < radius2) {
        values.put("z", radius2);
    }

    double tempcordx = values.get("x");
    double tempcordz = values.get("z");

    int cordx = (int) Math.round(tempcordx);
    int cordz = (int) Math.round(tempcordz);

    int cordy = loc.getWorld().getHighestBlockAt(cordx, cordz).getY();

    System.out.println(cordx+ " " + cordy + " " + cordz);

    Location tploc = new Location(p.getWorld(), cordx, cordy, cordz);

    Block cordBlock = loc.getWorld().getBlockAt(cordx, cordy, cordz);

    if(cordBlock.getType() != Material.LAVA ||
            cordBlock.getType() != Material.WATER ||
            cordBlock.getType() != Material.AIR) {
        p.teleport(tploc);
        System.out.println("Safe Teleport");
    }
}
}
