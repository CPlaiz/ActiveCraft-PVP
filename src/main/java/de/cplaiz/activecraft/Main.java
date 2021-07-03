package de.cplaiz.activecraft;

import de.cplaiz.activecraft.Varo.Command.*;
import de.cplaiz.activecraft.Varo.Listener.KickTimer;
import de.cplaiz.activecraft.Varo.Listener.StatsListener;
import de.cplaiz.activecraft.Varo.Timer;
import de.cplaiz.activecraft.commands.*;
import de.cplaiz.activecraft.discord.listener.CommandListener;
import de.cplaiz.activecraft.listener.JoinQuitListener;
import de.cplaiz.activecraft.listener.TeleportInsideBorderListener;
import de.cplaiz.activecraft.listener.discord.DiscordListener;
import de.cplaiz.activecraft.utils.Config;
import de.cplaiz.activecraft.utils.ReloadCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class Main extends JavaPlugin {

    private Config locations;
    private Config config;
    private Config playerlist;
    private Config nameuuidlist;
    private Config data;

    public static String PREFIX = ChatColor.GREEN + "ActiveCraft §f";
    public static String NOPERMISSION = ChatColor.RED + "You don't have the permission to do that!";
    public static String INVALIDARGS = ChatColor.RED + "Invalid arguments!" + ChatColor.GRAY + " Please recheck the command!";
    public static String INVALIDPLAYER = ChatColor.RED + "Invalid player!" + ChatColor.GRAY + " Please recheck the command!";


    public static Main INSTANCE;

    public JDA bot = null;

    private CommandManager cmdMan;

    private boolean useHolographicDisplays;

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

        saveDefaultConfig();

        File file = new File(getDataFolder(), "data.yml");

        if(!file.exists()) {
            saveResource("data.yml", false);
        }

        if (getConfig().getBoolean("test")){
            getLogger().info(getConfig().getString("test.msg"));
        }
        locations = new Config("locations.yml" , getDataFolder());
        config = new Config("config.yml" , getDataFolder());
        playerlist = new Config("playerlist.yml" , getDataFolder());
        nameuuidlist = new Config("nameuuidlist.yml" , getDataFolder());
        data = new Config("data.yml" , getDataFolder());

        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

        String BotToken = getConfig().getString("BotToken");
        JDABuilder builder = JDABuilder.createDefault(BotToken);
        builder.setActivity(Activity.playing("PvP Event hosted by Silencio"));

        this.cmdMan = new CommandManager();

        //discord listeners
        builder.addEventListeners(new CommandListener());

        try {
            bot = builder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Discord Bot has started.");

          plugin = this;




        String WebhookURL = getConfig().getString("WebhookURL");


        // Plugin startup logic
        this.register();
        log("§7Plugin loaded");
        try {
           url = new URL(WebhookURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        timer = new Timer(false, 0);

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        log("§7Plugin unloaded");
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
        //pluginManager.registerEvents(new TeleportInsideBorderListener(), this);



    //commands
        Bukkit.getPluginCommand("spawn").setExecutor(new SpawnCommand());
        Bukkit.getPluginCommand("canceltimer").setExecutor(new TimerCancelCommand());
        Bukkit.getPluginCommand("setepisodes").setExecutor(new SetEpisodes());
        Bukkit.getPluginCommand("setteam").setExecutor(new SetTeamName());
        Bukkit.getPluginCommand("setkills").setExecutor(new SetKills());
        Bukkit.getPluginCommand("setalive").setExecutor(new SetAlive());
        Bukkit.getPluginCommand("duty").setExecutor(new OnDutyCommand());
        Bukkit.getPluginCommand("border").setExecutor(new OutsideBorderTest());
        //Bukkit.getPluginCommand("activecraft-reload").setExecutor(new ReloadCommand());
        Bukkit.getPluginCommand("stats").setExecutor(new StatsMessageCommand());
        Bukkit.getPluginCommand("varo-start").setExecutor(new KickTimer());
        Bukkit.getPluginCommand("varo-stop").setExecutor(new StopCommand());
        Bukkit.getPluginCommand("playerdata-reset").setExecutor(new PlayerdataResetCommand());

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

    public CommandManager getCmdMan() {
        return cmdMan;
    }
}
