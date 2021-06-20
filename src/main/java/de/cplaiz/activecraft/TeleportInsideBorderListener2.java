package de.cplaiz.activecraft;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class TeleportInsideBorderListener2 implements Listener {

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

    System.out.println(radius);
    System.out.println(tempcordx + " " + tempcordz);
    }

    @EventHandler
    public void onWorldChange(PlayerTeleportEvent event) {

    Location to = event.getTo();
    Location from = event.getFrom();

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

        System.out.println(radius);
        System.out.println(tempcordx + " " + tempcordz);
    }

}
