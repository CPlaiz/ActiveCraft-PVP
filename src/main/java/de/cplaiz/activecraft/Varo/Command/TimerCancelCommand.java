package de.cplaiz.activecraft.Varo.Command;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.Varo.Listener.KickTimer;
import de.cplaiz.activecraft.utils.Config;
import de.cplaiz.activecraft.utils.FileConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TimerCancelCommand extends KickTimer implements CommandExecutor {

    Config playerdata;




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

         HashMap<UUID, Integer> timerActive2 = getTimerActive();
         HashMap<UUID, Player> playerStoring2 = getPlayerStoring();


        if (sender.hasPermission("timer.cancel") || sender.isOp()) {
            if(args.length == 1) {
                Player ment = Main.getPlugin().getServer().getPlayer(args[0]);
                FileConfig fileConfig = new FileConfig("playerdata/" + ment.getUniqueId().toString() + ".yml");
                int taskid = fileConfig.getInt("taskid");
                Bukkit.getScheduler().cancelTask(taskid);
                timerActive.remove(ment.getUniqueId());
                playerStoring.remove(ment.getUniqueId());
                //System.out.println(playerStoring.get(ment.getUniqueId()) + " addaadadadda " + timerActive.get(ment.getUniqueId()));
                sender.sendMessage("§6Timer cancelled for " + ment.getName() + "!");
                ment.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Timer cancelled!"));
            }else {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    FileConfig fileConfig = new FileConfig("playerdata/" + p.getUniqueId().toString() + ".yml");
                    int taskid = fileConfig.getInt("taskid");
                    Bukkit.getScheduler().cancelTask(taskid);
                    timerActive.remove(p.getUniqueId());
                    playerStoring.remove(p.getUniqueId());
                    //System.out.println(playerStoring.get(p.getUniqueId()) + " addaadadadda " + timerActive.get(p.getUniqueId()));
                    timerActive.remove(p.getUniqueId());
                    playerStoring.remove(p.getUniqueId());
                    sender.sendMessage("§6Timer cancelled!");
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Timer cancelled!"));
                } else {
                    sender.sendMessage("You are not a player!");
                }

                }

        }

        return true;
    }
}
