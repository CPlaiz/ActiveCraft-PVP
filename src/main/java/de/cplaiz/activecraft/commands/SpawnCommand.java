package de.cplaiz.activecraft.commands;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import de.cplaiz.activecraft.utils.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        FileConfig spawns = new FileConfig("locations.yml");
        if (label.equalsIgnoreCase("setspawn")) {
            if (player.hasPermission("de.cplaiz.activecraft.setspawn")) {
                spawns.set("spawn", LocationUtils.loc2string(player.getLocation()));
                spawns.saveConfig();
                player.sendMessage(Main.PREFIX + "Set spawn.");

            } else {
                player.sendMessage(Main.PREFIX + "§cYou don't have the permission to do that!");
            }
            return true;
        }

        if (spawns.contains("spawn")) {
            LocationUtils.teleport(player, LocationUtils.str2loc(spawns.getString("spawn")));
        } else {
            player.sendMessage(Main.PREFIX + "§cNo spawn has been set yet!");
        }

        return true;
    }

}
