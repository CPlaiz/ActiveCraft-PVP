package de.cplaiz.activecraft.listener;

import de.cplaiz.activecraft.Main;
import de.cplaiz.activecraft.Varo.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Timer timer = Main.getINSTANCE().getTimer();

       timer.setRunning(true);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }


}
