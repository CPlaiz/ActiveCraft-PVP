package de.cplaiz.activecraft;

import de.cplaiz.activecraft.Varo.Command.*;
import de.cplaiz.activecraft.Varo.Listener.KickTimer;
import de.cplaiz.activecraft.Varo.Listener.StatsListener;
import de.cplaiz.activecraft.Varo.Timer;
import de.cplaiz.activecraft.commands.*;
import de.cplaiz.activecraft.listener.JoinQuitListener;
import de.cplaiz.activecraft.listener.discord.DiscordListener;
import de.cplaiz.activecraft.utils.Config;
import de.cplaiz.activecraft.utils.ConfigReloadCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class Main extends JavaPlugin {

    private Config locations;
    private Config config;

    public static String PREFIX = "§aActiveCraft §7§o";
    public static String NOPERMISSION = "§cYou don't have the permission to do that!";
    public static String INVALIDARGS = "§cInvalid arguments!";


    public static Main INSTANCE;

    public JDA bot = null;



    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    private Timer timer;

    private int taskid;

    private URL url;

    public Main() {
        INSTANCE = this;
    }

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        //Config leaderboardConfig;

        //File file = new File(Tutorial.getPlugin().getDataFolder() + "\\playerdata\\");
        //if(!file.exists()) {
        //    file.mkdir();
        //}

        //leaderboardConfig = new Config("playerdata/" + "playerlist" + ".yml", Tutorial.getPlugin().getDataFolder());


        //config kram in die konsole


        //jda bot
        JDABuilder builder = JDABuilder.createDefault("ODQ2NzE3MjA4NjA4NzY4MDAw.YKzk2Q.uyBASaTNGvuQzHxxsX51UmpUFpM");
        builder.setActivity(Activity.playing("PvP Event hosted by Silencio"));


        try {
            bot = builder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Discord Bot has started.");

        //Tutorial.INSTANCE.sendtodiscord("Server has started");

          plugin = this;

        saveDefaultConfig();

        if (getConfig().getBoolean("test")){
            getLogger().info(getConfig().getString("test.msg"));
        }
        locations = new Config("locations.yml" , getDataFolder());
        config = new Config("config.yml" , getDataFolder());



        String WebhookURL = getConfig().getString("WebhookURL");

        // Plugin startup logic
        this.register();
        log("Plugin loaded");
        try {
           url = new URL(WebhookURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        timer = new Timer(false, 0);





    }

    @Override
    public void onDisable() {



        //Tutorial.INSTANCE.sendtodiscord("Server has stopped");
        // Plugin shutdown logic
        log("Plugin unloaded");
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }


    private void register() {

    //listener

    PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        //pluginManager.registerEvents(new Navigator(1), this);
        pluginManager.registerEvents(new DiscordListener(), this);
        //pluginManager.registerEvents(new KickScheduler(), this);
        pluginManager.registerEvents(new KickTimer(), this);
        pluginManager.registerEvents(new StatsListener(), this);
        //pluginManager.registerEvents(new TeleportInsideBorderListener2(), this);



    //commands


        //Bukkit.getPluginCommand("heal").setExecutor(new HealCommand());
        Bukkit.getPluginCommand("spawn").setExecutor(new SpawnCommand());
        Bukkit.getPluginCommand("canceltimer").setExecutor(new TimerCancelCommand());
        Bukkit.getPluginCommand("setepisodes").setExecutor(new SetEpisodes());
        Bukkit.getPluginCommand("setteam").setExecutor(new SetTeamName());
        Bukkit.getPluginCommand("setkills").setExecutor(new SetKills());
        Bukkit.getPluginCommand("setalive").setExecutor(new SetAlive());
        Bukkit.getPluginCommand("duty").setExecutor(new OnDutyCommand());
        Bukkit.getPluginCommand("activecraft-reload").setExecutor(new ConfigReloadCommand());
        Bukkit.getPluginCommand("stats").setExecutor(new StatsMessageCommand());
        Bukkit.getPluginCommand("varo-start").setExecutor(new KickTimer());
        //Bukkit.getPluginCommand("fly").setExecutor(new FlyCommand());
        //Bukkit.getPluginCommand("item").setExecutor(new ItemCommand());
        //Bukkit.getPluginCommand("admin-items").setExecutor(new AdminItemsCommand());
       // Bukkit.getPluginCommand("seeinv").setExecutor(new SeeInv());
    }

    public void sendtodiscord(String content) {
        try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream stream = connection.getOutputStream();
            stream.write(jsonObject.toJSONString().getBytes());
            stream.flush();
            stream.close();

            connection.getInputStream().close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendfulltodiscord(String embeds) {
        try {
            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("content", content);
            jsonObject.put("embeds", embeds);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream stream = connection.getOutputStream();
            stream.write(jsonObject.toJSONString().getBytes());
            stream.flush();
            stream.close();

            connection.getInputStream().close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config getLocations(){
        return locations;

    }
    public Config getMainConfig() {
        return config;
    }

    public Timer getTimer() {
        return timer;
    }

    public static Main getINSTANCE() {
        return INSTANCE;
    }
}
