package de.cplaiz.activecraft.Varo.Command;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;



public class OnDutyCommand implements CommandExecutor, TabCompleter {

    String dutyOnCommand = Main.getPlugin().getConfig().getString("DutyOnCommand");
    String dutyOffCommand = Main.getPlugin().getConfig().getString("DutyOffCommand");

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("duty") || sender.isOp()) {
            if (args.length == 2) {
                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);
                FileConfig fileConfig = new FileConfig("playerdata/" + ment.getUniqueId().toString() + ".yml");
                switch (args[1].toLowerCase()) {
                    case "on":
                        fileConfig.set("onDuty", true);
                        fileConfig.saveConfig();
                        sender.sendMessage("§6You are now on duty.");
                        Bukkit.dispatchCommand(console, dutyOnCommand.replace("%playername%", ment.getName()));
                        break;
                    case "off":
                        fileConfig.set("onDuty", false);
                        fileConfig.saveConfig();
                        sender.sendMessage("§6You are not on Duty anymore.");
                        Bukkit.dispatchCommand(console, dutyOffCommand.replace("%playername%", ment.getName()));
                        break;
                }
            } else if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = ((Player) sender).getPlayer();
                    FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

                    switch (args[0].toLowerCase()) {
                        case "on":
                            fileConfig.set("onDuty", true);
                            fileConfig.saveConfig();
                            sender.sendMessage("§6You are now on duty.");
                            Bukkit.dispatchCommand(console, dutyOnCommand.replace("%playername%", p.getName()));
                            break;
                        case "off":
                            fileConfig.set("onDuty", false);
                            fileConfig.saveConfig();
                            sender.sendMessage("§6You are not on Duty anymore.");
                            Bukkit.dispatchCommand(console, dutyOffCommand.replace("%playername%", p.getName()));
                            break;

                    }
                }

            }

            }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 1) {
            list.add("on");
            list.add("off");
        }else if (args.length == 2) {
           list.add("on");
           list.add("off");
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
