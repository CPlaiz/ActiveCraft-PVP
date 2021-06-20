package de.cplaiz.activecraft.commands;

import de.cplaiz.activecraft.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            sender.sendMessage("Item");
            ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("Genjis Dragonblade");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Ryūjin no ken wo kurae");
            lore.add("The dragon becomes me");
            meta.setLore(lore);
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);
        }else {
            sender.sendMessage(Main.PREFIX + "§c§lYou're not a player!");
        }
        return false;

    }
}
