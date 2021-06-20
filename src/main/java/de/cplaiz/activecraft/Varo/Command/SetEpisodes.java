package de.cplaiz.activecraft.Varo.Command;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetEpisodes implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("episodes.set") || sender.isOp()) {
            if (args.length == 2) {
                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);
                if (ment != null) {
                    int episodes = Integer.parseInt(args[1]);
                    FileConfig fileConfig = new FileConfig("playerdata/" + ment.getUniqueId().toString() + ".yml");

                    fileConfig.set("episodes", episodes);
                    fileConfig.saveConfig();

                    sender.sendMessage(ChatColor.GOLD + "Episodes for " + ment.getName() + " is now " + episodes);

                } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
            } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
        } else sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");

        return true;
    }

    @Override
    public List<String> onTabComplete( CommandSender sender,  Command command,  String alias,  String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 1) {
            for (Player p : Main.getPlugin().getServer().getOnlinePlayers()) {
            list.add(p.getName().toLowerCase());
        }
        } else if (args.length == 2) {
            for(int i = 0; i < 100; i++) {
                list.add(String.valueOf(i));
            }
        }
        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)){
                completerList.add(s);
            }
        }

        return completerList;
    }

}
