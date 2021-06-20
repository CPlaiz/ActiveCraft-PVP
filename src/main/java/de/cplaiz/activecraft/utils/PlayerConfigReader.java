package de.cplaiz.activecraft.utils;

import com.google.gson.Gson;
import org.bukkit.event.Listener;

import java.io.File;

public class PlayerConfigReader implements Listener {


    private Gson gson = new Gson();
    private final File file = new File("plugins//ActiveCraft//PlayerConfigs//");

    public void saveConfig() {

        PlayerConfig playerConfig = new PlayerConfig("", "",0, 0, "", true);
    }

    public Boolean readConfig (){
    return true;
    }

}
