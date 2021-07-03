package de.cplaiz.activecraft.Varo.Command;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetTeamName implements CommandExecutor , TabCompleter {

    FileConfig nameuuidlist = new FileConfig("nameuuidlist.yml");
    FileConfig config = new FileConfig("config.yml");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("team.name.set") || sender.isOp()) {
            if (args.length == 2) {
                String uuid = nameuuidlist.getString(args[0].toLowerCase());
                File file = new File(Main.getPlugin().getDataFolder() + File.separator + "playerdata" + File.separator + uuid + ".yml");

                if (file.exists()) {
                if (uuid != null) {
                    FileConfig fileConfig = new FileConfig("playerdata/" + uuid + ".yml");
                    String teamName = args[1];
                    fileConfig.set("team.name", teamName);
                    fileConfig.saveConfig();

                    sender.sendMessage(ChatColor.GOLD + "Team for " + ChatColor.AQUA + args[0] + ChatColor.GOLD + " is now " + ChatColor.GREEN + teamName);

                    } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
                } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
            } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
        } else sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 1) {
            for (Player p : Main.getPlugin().getServer().getOnlinePlayers()) {
                list.add(p.getName().toString());
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1];
        for (String s : list) {
            String s1 = s;
            if (s1.startsWith(currentarg)){
                completerList.add(s);
            }
        }

        return completerList;
    }
}
