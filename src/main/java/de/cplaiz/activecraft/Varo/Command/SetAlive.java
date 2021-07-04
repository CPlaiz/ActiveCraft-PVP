package de.cplaiz.activecraft.Varo.Command;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.FileConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class SetAlive implements CommandExecutor, TabCompleter {

    FileConfig nameuuidlist = new FileConfig("nameuuidlist.yml");

    ArrayList<String> teamnamestoring = new ArrayList<>();

    JDA jda = Main.getPlugin().bot;

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("alive.set") || sender.isOp()) {
            if (args.length == 2) {
                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);
                String uuid = nameuuidlist.getString(args[0].toLowerCase());
                File file = new File(Main.getPlugin().getDataFolder() + File.separator + "playerdata" + File.separator + uuid + ".yml");

                if (file.exists()) {
                if (uuid != null) {
                    //System.out.println(uuid);
                    FileConfig fileConfig = new FileConfig("playerdata/" + uuid + ".yml");
                    FileConfig dataConfig = new FileConfig("data.yml");
                    switch (args[1].toLowerCase()) {
                        case "true":
                            fileConfig.set("is-alive", true);
                            fileConfig.saveConfig();
                            sender.sendMessage("§6Status for " + args[0] + ("§6 is now §a§lALIVE"));
                            //editDeathOverview();
                            break;
                        case "false":
                            fileConfig.set("is-alive", false);
                            fileConfig.saveConfig();
                            sender.sendMessage("§6Status for " + args[0] + ("§6 is now §c§lDEAD"));
                            //editDeathOverview();
                            break;
                    }
                    } else sender.sendMessage(Main.INVALIDARGS);
            } else sender.sendMessage(Main.INVALIDPLAYER);

            } else if (args.length == 1) {
                if (sender instanceof Player) {
                    Player p = ((Player) sender).getPlayer();
                    FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

                    switch (args[0].toLowerCase()) {
                        case "true":
                            fileConfig.set("is-alive", true);
                            fileConfig.saveConfig();
                            sender.sendMessage(ChatColor.GOLD + "Status for " + ChatColor.AQUA + p.getName() + ChatColor.GOLD + (" is now §a§lALIVE"));
                            //editDeathOverview();
                            break;
                        case "false":
                            fileConfig.set("is-alive", false);
                            fileConfig.saveConfig();
                            sender.sendMessage(ChatColor.GOLD + "Status for " + ChatColor.AQUA + p.getName() + ChatColor.GOLD + (" is now §c§lDEAD"));
                            //editDeathOverview();
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
            list.add("true");
            list.add("false");
        }else if (args.length == 2) {
            list.add("true");
            list.add("false");
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

    public void editDeathOverview() {
        FileConfig config = new FileConfig("config.yml");
        FileConfig dataConfig = new FileConfig("data.yml");

        long messageid = dataConfig.getLong("death-message-id");
        long channelid = config.getLong("Death-Message-Channel-Id");
        TextChannel textChannel = jda.getTextChannelById(channelid);

        EmbedBuilder deathOverview = new EmbedBuilder();

        FileConfig participantsList = new FileConfig("participants.yml");
        List<String> players = participantsList.getStringList("participants");
        for (String uuid : players) {
            FileConfig playerdataConfig = new FileConfig("playerdata/" + uuid + ".yml");
            String name =  playerdataConfig.getString("name");
            String teamname =  playerdataConfig.getString("team.name");
            boolean alive = playerdataConfig.getBoolean("is-alive");
            List<String> players2 = participantsList.getStringList("participants");
            players2.remove(uuid);

            for (String uuid2 : players2) {
                System.out.println(uuid2);
                FileConfig playerdataConfig2 = new FileConfig("playerdata/" + uuid2 + ".yml");
                String name2 =  playerdataConfig2.getString("name");
                String teamname2 =  playerdataConfig2.getString("team.name");
                boolean alive2 = playerdataConfig2.getBoolean("is-alive");
                System.out.println(alive + " " + alive2);
                if (teamname.equals(teamname2) && !teamnamestoring.contains(teamname)) {
                    if (alive && alive2) {
                        deathOverview.addField(teamname, name + ":white_check_mark:\n" + name2 + ":white_check_mark:", true);
                        teamnamestoring.add(teamname);
                    } else if(alive && !alive2) {
                        deathOverview.addField(teamname, name + ":white_check_mark:\n" + name2 + ":x:", true);
                        teamnamestoring.add(teamname);
                    } else if(!alive && alive2) {
                        deathOverview.addField(teamname, name + ":x:\n" + name2 + ":white_check_mark:", true);
                        teamnamestoring.add(teamname);
                    } else if(!alive && !alive2) {
                        deathOverview.addField(teamname, name + ":x:\n" + name2 + ":x:", true);
                        teamnamestoring.add(teamname);
                    }

                }
                //players.remove(uuid);


                deathOverview.setColor(0x000000);
                deathOverview.setTitle("Death Overview");
            }

        }

        textChannel.editMessageEmbedsById(messageid, deathOverview.build()).queue();
    }

}
