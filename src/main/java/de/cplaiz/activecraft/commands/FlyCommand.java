package de.cplaiz.activecraft.commands;

import de.cplaiz.activecraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("fly.own")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage("§aFlight mode enabled!");
                }
                else {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage("§cFlight mode disabled!");
                }
            } else {
                sender.sendMessage(Main.PREFIX + "§4§lYou don't have the permission to do that!");
            }
        }

        return true;
    }
}
