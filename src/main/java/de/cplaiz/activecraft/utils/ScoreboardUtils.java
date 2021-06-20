package de.cplaiz.activecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {

    public static Scoreboard getBaseScoreboard(Player player){

        Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = s.registerNewObjective("main" , "main" , "§6§lPVP Event");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String Alive1;
        String Alive2;
        String Alive3;
        String Alive4;
        String Alive5;
        String Alive6;
        String Alive7;
        String Alive8;

        Alive1 = "§aALIVE";
        Alive2 = "§eALIVE";
        Alive3 = "§aALIVE";
        Alive4 = "§aALIVE";
        Alive5 = "§4DEAD";
        Alive6 = "§eALIVE";
        Alive7 = "§4DEAD";
        Alive8 = "§aALIVE";


        objective.getScore("testservermc.ddns.net").setScore(0);
        objective.getScore("§eTeam 4   §l§fStatus: " + Alive4).setScore(1);
        objective.getScore("§9Team 3   §l§fStatus: " + Alive3).setScore(2);
        objective.getScore("§cTeam 2   §l§fStatus: " + Alive2).setScore(3);
        objective.getScore("§aTeam 1   §l§fStatus: " + Alive1).setScore(4);

        objective.getScore("§l").setScore(5);

        objective.getScore("§lYour Team: ").setScore(6);





        return s;

    }

}
