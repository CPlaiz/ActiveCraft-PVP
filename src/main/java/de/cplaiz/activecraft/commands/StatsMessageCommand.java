package de.cplaiz.activecraft.commands;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatsMessageCommand implements CommandExecutor, TabCompleter {

    FileConfig nameuuidlist = new FileConfig("nameuuidlist.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("stats.view") || sender.isOp()) {
            if (args.length == 1) {


                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);

                String uuid = nameuuidlist.getString(args[0].toLowerCase());

                File file = new File(Main.getPlugin().getDataFolder() + File.separator + "playerdata" + File.separator + uuid + ".yml");

                if (file.exists()) {

                    FileConfig fileConfig = new FileConfig("playerdata/" + uuid + ".yml");
                    int killscount = fileConfig.getInt("stats.kills");
                    int episodescount = fileConfig.getInt("episodes");
                    boolean alive = fileConfig.getBoolean("isalive");
                    String teamnamestring = fileConfig.getString("team.name");
                    String namestring = fileConfig.getString("name");
                    String uuidstring = fileConfig.getString("uuid");

                    String splitter = "§6---------------\n";
                    String header = "§l§6" + namestring + "'s Stats: \n";
                    String name = "§bName§f: " + namestring + "\n";
                    String teamname = "§bTeam§f: " + teamnamestring + "\n";
                    String isalive;
                    String kills = "§bKills§f: " + killscount + "\n";
                    String episodes = "§bEpisodes§f: " + episodescount + "\n";
                    if (alive) {
                        isalive = "§bAlive§f: " + ChatColor.GREEN + alive + "\n";
                        sender.sendMessage(splitter + header + name + teamname + isalive + kills + episodes + splitter);
                    }
                    if (!alive) {
                        isalive = "§bAlive§f: " + ChatColor.RED + alive + "\n";
                        sender.sendMessage(splitter + header + name + teamname + isalive + kills + episodes + splitter);
                    }
                }else sender.sendMessage(Main.INVALIDPLAYER);

            } else if (args.length == 0) {
                Player player = (Player) sender;

                //String uuid = nameuuidlist.getString(args[0].toLowerCase());



                    FileConfig fileConfig = new FileConfig("playerdata/" + player.getUniqueId().toString() + ".yml");
                    int killscount = fileConfig.getInt("stats.kills");
                    int episodescount = fileConfig.getInt("episodes");
                    boolean alive = fileConfig.getBoolean("isalive");
                    String teamnamestring = fileConfig.getString("team.name");
                    String namestring = fileConfig.getString("name");
                    String uuidstring = fileConfig.getString("uuid");

                    String splitter = "§6---------------\n";
                    String header = "§l§6Your Stats: \n";
                    String name = "§bName§f: " + namestring + "\n";
                    String teamname = "§bTeam§f: " + teamnamestring + "\n";
                    String isalive;
                    String kills = "§bKills§f: " + killscount + "\n";
                    String episodes = "§bEpisodes§f: " + episodescount + "\n";
                    if (alive) {
                        isalive = "§bAlive§f: " + ChatColor.GREEN + alive + "\n";
                        sender.sendMessage(splitter + header + name + teamname + isalive + kills + episodes + splitter);
                    }
                    if (!alive) {
                        isalive = "§bAlive§f: " + ChatColor.RED + alive + "\n";
                        sender.sendMessage(splitter + header + name + teamname + isalive + kills + episodes + splitter);

                } else sender.sendMessage(Main.INVALIDPLAYER);
            }

        } else sender.sendMessage(Main.NOPERMISSION);


        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 1) {
            for (Player p : Main.getPlugin().getServer().getOnlinePlayers()) {
                list.add(p.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1];
        for (String s : list) {
            if (s.startsWith(currentarg)){
                completerList.add(s);
            }
        }

        return completerList;
    }


}
