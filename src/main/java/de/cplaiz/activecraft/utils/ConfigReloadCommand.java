package de.cplaiz.activecraft.utils;

import de.cplaiz.activecraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigReloadCommand implements CommandExecutor {

    YamlConfiguration conf = new YamlConfiguration();
    File cfile = new File(Main.getPlugin().getDataFolder(), "config.yml");
    Config config = Main.getPlugin().getMainConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        config.reload();
        config.save();

        sender.sendMessage("§6Config reloaded");

        return true;
    }
}
