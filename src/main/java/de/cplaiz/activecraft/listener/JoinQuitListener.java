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
    boolean logWorldChanges = Main.getPlugin().getConfig().getBoolean("LogWorldChanges");
    List<String> commandExecutedOnPlayerDeath = Main.getPlugin().getConfig().getStringList("CommandExecutedOnPlayerDeath");


    Long tcid = Long.parseLong(tcidraw);

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    JDA jda = Main.getPlugin().bot;

    String name = jda.getSelfUser().getName();

    TextChannel tc = jda.getTextChannelById(tcid);

    String worldName = Main.getPlugin().getConfig().getString("WorldName");

    @EventHandler
    public void onPlayerWorldChange(PlayerTeleportEvent event) {

        System.out.println(name);

        Location to = event.getTo();
        Location from = event.getFrom();
        Player p = event.getPlayer();

        FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
        FileConfig dataConfig = new FileConfig("data.yml");


        if (to.getWorld() != from.getWorld() && logWorldChanges) {
            System.out.println(ChatColor.GOLD + p.getName() + " changed from " + from.getWorld().getName() + " to " + to.getWorld().getName());
        }

        int episodesplus1 = fileConfig.getInt("episodes") + 1;
        String teamRoleId = fileConfig.getString("team.roleid");
        String teamName = fileConfig.getString("team.name");

        EmbedBuilder joinBuilder = new EmbedBuilder();
        joinBuilder.setTitle("**" + p.getName() + "** joined");
        joinBuilder.addField("**Team**", "" + teamName, true);
        joinBuilder.addField("**Folge**", "" + episodesplus1, true);
        joinBuilder.setColor(0x55ffff);
        joinBuilder.setTimestamp(OffsetDateTime.now());

        boolean onDuty = fileConfig.getBoolean("on-duty");

        if (dataConfig.getBoolean("is-active")) {

            if (!onDuty) {


                if (to.getWorld() != from.getWorld()) {


                    if (to.getWorld().getName().equals(worldName) && !from.getWorld().getName().equals(worldName + "_nether")) {

                        tc.sendMessageEmbeds(joinBuilder.build()).queue();

                    }
                    if (to.getWorld().getName().equals(worldName + "_nether") && !from.getWorld().getName().equals(worldName)) {
                        tc.sendMessageEmbeds(joinBuilder.build()).queue();
                    }
                    if (from.getWorld().getName().equals(worldName) && !to.getWorld().getName().equals(worldName + "_nether")) {
                        EmbedBuilder leaveBuilder = new EmbedBuilder();
                        leaveBuilder.setTitle("**" + p.getName() + "** left");
                        leaveBuilder.addField("**Team**", "" + teamName, true);
                        leaveBuilder.setColor(0xaa5555);
                        leaveBuilder.setTimestamp(OffsetDateTime.now());
                        tc.sendMessageEmbeds(leaveBuilder.build()).queue();
                    }
                    if (from.getWorld().getName().equals(worldName + "_nether") && !to.getWorld().getName().equals(worldName)) {
                        EmbedBuilder leaveBuilder = new EmbedBuilder();
                        leaveBuilder.setTitle("**" + p.getName() + "** left");
                        leaveBuilder.addField("**Team**", "" + teamName, true);
                        leaveBuilder.setColor(0xaa5555);
                        leaveBuilder.setTimestamp(OffsetDateTime.now());
                        tc.sendMessageEmbeds(leaveBuilder.build()).queue();
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();


        FileConfig fileConfigPlayers = new FileConfig("playerlist.yml");

        List<String> playerlist = fileConfigPlayers.getStringList("players");
        if (!playerlist.contains(p.getUniqueId().toString())) {
            playerlist.add(p.getUniqueId().toString());
        }
        fileConfigPlayers.set("players", playerlist);
        fileConfigPlayers.saveConfig();


        FileConfig fileConfigUUIDName = new FileConfig("nameuuidlist.yml");

        fileConfigUUIDName.set(p.getName().toLowerCase(), p.getUniqueId().toString());
        fileConfigUUIDName.saveConfig();


        File file = new File(Main.getPlugin().getDataFolder() + File.separator + "playerdata" + File.separator);

        Config config;

        if (!file.exists()) {
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
            fileConfig.set("is-alive", true);
            fileConfig.set("stats.kills", 0);
            fileConfig.set("onDuty", false);


            fileConfig.saveConfig();

        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        FileConfig dataConfig = new FileConfig("data.yml");

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

        boolean onDuty = fileConfig.getBoolean("on-duty");

        if (dataConfig.getBoolean("is-active")) {
            if (!onDuty) {

                p.performCommand(commandExecutedOnJoinQuit);
                World from = p.getWorld();
                if (from.getName().equals(worldName)) {
                    tc.sendMessageEmbeds(leaveBuilder.build()).queue();
                }
                if (from.getName().equals(worldName + "_nether")) {

                    tc.sendMessageEmbeds(leaveBuilder.build()).queue();
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String deathmessage = event.getDeathMessage();

        Player p = event.getEntity();

        FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
        FileConfig dataConfig = new FileConfig("data.yml");

        int episodes = fileConfig.getInt("episodes");
        String teamRoleId = fileConfig.getString("team.roleid");
        String teamName = fileConfig.getString("team.name");

        boolean onDuty = fileConfig.getBoolean("on-duty");

        if (dataConfig.getBoolean("is-active")) {

            if (!onDuty) {

                if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(worldName + "_nether")) {

                    fileConfig.set("is-alive", false);

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
                    tc.sendMessageEmbeds(deathBuilder.build()).queue();
                }
            }
        }
    }
}
