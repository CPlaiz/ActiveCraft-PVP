package de.cplaiz.activecraft;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestClassAgain implements Listener {


    JDA bot;
    TextChannel textChannel;
    public void ChatToDiscord(JDA bot) {
        this.bot = bot;
        textChannel = bot.getTextChannelById(702812269469237348L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getJoinMessage();
    }

}
