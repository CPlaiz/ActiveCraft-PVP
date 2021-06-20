package de.cplaiz.activecraft.commands;

import de.cplaiz.activecraft.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))   {
            Main.INSTANCE.log("Du bist kein Spieler!");
            return true;
        }

        Player player = (Player) sender;
        if(player.hasPermission("de.cplaiz.activecraft.heal")) {
            player.setHealth(20d);
            player.setFoodLevel(20);
            player.sendMessage(Main.PREFIX + "You have been healed");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.2f, 1.2f);
        } else {
            player.sendMessage(Main.PREFIX + "§4§lYou don't have the permission to do that!");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 0.2f, 1.4f);
        }

        return true;
    }
}
