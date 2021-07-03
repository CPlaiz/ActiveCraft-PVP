package de.cplaiz.activecraft.listener;

import de.cplaiz.activecraft.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class TeleportInsideBorderListener implements Listener {

    String worldName = Main.getPlugin().getConfig().getString("WorldName");

    double locx;
    double locz;

    @EventHandler
    public void onWorldChange(PlayerTeleportEvent event) {

    Player p = event.getPlayer();
    Location from = event.getFrom();
    Location to = event.getTo();

    if (!to.getWorld().getName().equals(from.getWorld().getName())) {
        if (to.getWorld().getName().equals(worldName) || to.getWorld().getName().equals(worldName + "_nether")) {

            System.out.println(from.getWorld().getName());

            if (isOutsideOfBorder(p)) {
                System.out.println("outside");


                WorldBorder border = p.getWorld().getWorldBorder();
                Location loc = p.getLocation();
                Location center = border.getCenter();
                double size = border.getSize() / 2;

                if (loc.getBlockX() > size - center.getBlockX()) {
                    locx = size - 1 - center.getBlockX();
                    System.out.println("x außerhalb");
                } else if (loc.getBlockX() < -size - center.getBlockX()) {
                    locx = -size + 1 - center.getBlockX();
                    System.out.println("-x außerhalb");
                } else locx = loc.getX() - center.getBlockX();


                if (loc.getBlockZ() > size - center.getBlockZ()) {
                    locz = size - 1 - center.getBlockZ();
                    System.out.println("z außerhalb");
                } else if (loc.getBlockZ() < -size - center.getBlockZ()) {
                    locz = -size + 1 - center.getBlockZ();
                    System.out.println("-z außerhalb");
                } else locz = loc.getZ() - center.getBlockZ();


                int intx = (int) Math.round(locx);
                int intz = (int) Math.round(locz);

                System.out.println("intx: " + intx + ", intz: " + intz);


                int y = loc.getWorld().getHighestBlockYAt((int) Math.round(locx), (int) Math.round(locz));

                Location tploc = new Location(p.getWorld(), locx, y + 1, locz, loc.getYaw(), loc.getPitch());

                p.teleport(tploc);
            } else System.out.println("inside");

            System.out.println("einfach test obs überhaupt klappt");
        }else System.out.println("welt ist nicht die config welt");
    } else System.out.println("to und from sind gleich");
}

    public boolean isOutsideOfBorder(Player p) {
        Location loc = p.getLocation();
        WorldBorder border = p.getWorld().getWorldBorder();
        double x = loc.getX();
        double z = loc.getZ();
        double size = border.getSize();
        return ((x > size || (-x) > size) || (z > size || (-z) > size));
    }

}
