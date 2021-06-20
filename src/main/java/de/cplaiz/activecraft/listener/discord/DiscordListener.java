package de.cplaiz.activecraft.listener.discord;

import de.cplaiz.activecraft.Main;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DiscordListener implements Listener {

public void onAction(PlayerCommandPreprocessEvent event) {
    Main.INSTANCE.sendtodiscord("**" + event.getPlayer().getName() + ":** " + event.getMessage());
}


}
