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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetTeamName implements CommandExecutor , TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("team.name.set") || sender.isOp()) {
            if (args.length == 2) {
                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);
                if (ment != null) {
                    String teamName = args[1];
                    FileConfig fileConfig = new FileConfig("playerdata/" + ment.getUniqueId().toString() + ".yml");

                    fileConfig.set("team.name", teamName);
                    fileConfig.saveConfig();


                } else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
            }   else sender.sendMessage(ChatColor.GOLD + "This is not a valid player!");
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
                list.add(p.getName().toString().toLowerCase());
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1];
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)){
                completerList.add(s);
            }
        }

        return completerList;
    }
}
