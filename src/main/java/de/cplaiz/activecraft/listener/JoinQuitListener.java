package de.cplaiz.activecraft.listener;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.utils.Config;
import de.cplaiz.activecraft.utils.FileConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.List;

public class JoinQuitListener implements Listener {

    String tcidraw = Main.getPlugin().getConfig().getString("MessageChannel");
    String commandExecutedOnJoinQuit = Main.getPlugin().getConfig().getString("CommandExecutedOnJoinQuit");
    List<String> commandExecutedOnPlayerDeath = Main.getPlugin().getConfig().getStringList("CommandExecutedOnPlayerDeath");


    Long tcid = Long.parseLong(tcidraw);

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    JDA bot = Main.getPlugin().bot;

    String name = bot.getSelfUser().getName();

    TextChannel tc = bot.getTextChannelById(tcid);

    //extChannel tc = bot.getTextChannelById(846779760713793558L); //webhook trash notes von cp
    //TextChannel tc = bot.getTextChannelById(845229956962320415L); //joinen pvp event


    String worldName = Main.getPlugin().getConfig().getString("WorldName");

    @EventHandler
    public void onPlayerWorldChange(PlayerTeleportEvent event) {

        Location to = event.getTo();
        Location from = event.getFrom();
        Player p = event.getPlayer();

        System.out.println(ChatColor.GOLD + p.getName() + " changed from " + from.getWorld().getName() + " to " + to.getWorld().getName());

        //tc.sendMessage("okay").queue();
        //System.out.println(tcid);
        //System.out.println(name);
        FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

        int episodesplus1 = fileConfig.getInt("episodes") + 1;
        String teamRoleId = fileConfig.getString("team.roleid");
        String teamName = fileConfig.getString("team.name");

        EmbedBuilder joinBuilder = new EmbedBuilder();
        joinBuilder.setTitle("**" + p.getName() + "** joined");
        joinBuilder.addField("**Team**", "" + teamName, true);
        joinBuilder.addField("**Folge**", "" + episodesplus1, true);
        joinBuilder.setColor(0x55ffff);
        joinBuilder.setTimestamp(OffsetDateTime.now());


        //System.out.println(tc.toString() + " 2");

        boolean onDuty = fileConfig.getBoolean("onDuty");

        if(!onDuty) {

        if (to.getWorld() != from.getWorld()) {


            if (to.getWorld().getName().equals(worldName) && !from.getWorld().getName().equals(worldName + "_nether")) {

                tc.sendMessage(joinBuilder.build()).queue();

            }
            if (to.getWorld().getName().equals(worldName + "_nether") && !from.getWorld().getName().equals(worldName)) {
                tc.sendMessage(joinBuilder.build()).queue();
            }
            if (from.getWorld().getName().equals(worldName) && !to.getWorld().getName().equals(worldName + "_nether")) {
                EmbedBuilder leaveBuilder = new EmbedBuilder();
                leaveBuilder.setTitle("**" + p.getName() + "** left");
                leaveBuilder.addField("**Team**", "" + teamName, true);
                leaveBuilder.setColor(0xaa5555);
                leaveBuilder.setTimestamp(OffsetDateTime.now());
                tc.sendMessage(leaveBuilder.build()).queue();
            }
            if (from.getWorld().getName().equals(worldName + "_nether") && !to.getWorld().getName().equals(worldName)) {
                EmbedBuilder leaveBuilder = new EmbedBuilder();
                leaveBuilder.setTitle("**" + p.getName() + "** left");
                leaveBuilder.addField("**Team**", "" + teamName, true);
                leaveBuilder.setColor(0xaa5555);
                leaveBuilder.setTimestamp(OffsetDateTime.now());
                tc.sendMessage(leaveBuilder.build()).queue();
            }
        }
    }
        }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player p = event.getPlayer();

        Config config;

        File file = new File(Main.getPlugin().getDataFolder() + "\\playerdata\\");
        if(!file.exists()) {
            file.mkdir();
        }

        config = new Config("playerdata/" + p.getUniqueId().toString() + ".yml", Main.getPlugin().getDataFolder());

        if (config.toFileConfiguration().getKeys(true).size() == 0) {

            FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

            fileConfig.set("name", p.getName());
            fileConfig.set("uuid", p.getUniqueId().toString());
            fileConfig.set("team.roleid", "");
            fileConfig.set("team.name", "");
            fileConfig.set("episodes", 0);
            fileConfig.set("taskid", 0);
            fileConfig.set("isAlive", true);
            fileConfig.set("stats.kills", 0);
            fileConfig.set("onDuty", false);
            fileConfig.saveConfig();

            //System.out.println("hat geklappt");
        }else {
            //System.out.println("hat NICHT geklappt");
             }

        //System.out.println(tcid);

            //System.out.println(name);

            //System.out.println(tcid);

            Player player = event.getPlayer();

            //zeug für kick nach 15 min

            //scoreboard krams

            //event.getPlayer().setScoreboard(ScoreboardUtils.getBaseScoreboard(event.getPlayer()));


            //player.performCommand(commandExecutedOnJoinQuit);

            //event.setJoinMessage("§a§l>> §7" + player.getDisplayName());

            //Tutorial.INSTANCE.sendtodiscord(event.getPlayer().getName() + " joined");


            if (event.getPlayer().isOp()) {
                //TablistManager.getInstance().registerTeam(event.getPlayer() , "pepega " , EnumChatFormat.RED , " nix" , 0);
            } else {
                //TablistManager.getInstance().registerTeam(event.getPlayer() , "pepega " , EnumChatFormat.RED , " nix" , 0);
            }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        //System.out.println(name);

        //System.out.println(tcid);

        Player p = event.getPlayer();

        FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

        int episodes = fileConfig.getInt("episodes");
        String teamRoleId = fileConfig.getString("team.roleid");
        String teamName = fileConfig.getString("team.name");

        EmbedBuilder leaveBuilder = new EmbedBuilder();
        leaveBuilder.setTitle("**" + p.getName() + "** left");
        leaveBuilder.addField("**Team**", "" + teamName, true);
        leaveBuilder.setColor(0xaa5555);
        leaveBuilder.setTimestamp(OffsetDateTime.now());

        boolean onDuty = fileConfig.getBoolean("onDuty");

        if(!onDuty) {

        p.performCommand(commandExecutedOnJoinQuit);
        World from = p.getWorld();
        //event.setQuitMessage("§c§l<< §7" + player.getDisplayName());
        //Tutorial.INSTANCE.sendtodiscord(event.getPlayer().getName() + " left");
        if (from.getName().equals(worldName)) {
            //Tutorial.INSTANCE.sendtodiscord("moved to world " + worldName);

            tc.sendMessage(leaveBuilder.build()).queue();
        }
        if (from.getName().equals(worldName + "_nether")) {
            //Tutorial.INSTANCE.sendtodiscord("moved to world " + worldName);

            tc.sendMessage(leaveBuilder.build()).queue();
        }
    }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String deathmessage = event.getDeathMessage();

        Player p = event.getEntity();

        FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");

        int episodes = fileConfig.getInt("episodes");
        String teamRoleId = fileConfig.getString("team.roleid");
        String teamName = fileConfig.getString("team.name");

        boolean onDuty = fileConfig.getBoolean("onDuty");

        if (!onDuty) {

            if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {

                fileConfig.set("isAlive", false);

                for (String s : commandExecutedOnPlayerDeath) {
                    Bukkit.dispatchCommand(console, s.replace("%playername%", p.getName()));
                    System.out.println(s.replace("%playername%", p.getName()));
            }
                EmbedBuilder deathBuilder = new EmbedBuilder();
                deathBuilder.setTitle("**" + p.getName() + "** died ");
                deathBuilder.addField("**Team**", "" + teamName, true);
                deathBuilder.setDescription(deathmessage);
                deathBuilder.setColor(0x000000);
                deathBuilder.setTimestamp(OffsetDateTime.now());
                tc.sendMessage(deathBuilder.build()).queue();
            }
    }
    }
}
