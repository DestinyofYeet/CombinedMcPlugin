package me.destinyofyeet.CombinedMcPlugin.events;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class ScoreboardStuff implements Listener {
    private FileConfiguration config = Main.getPlugin().getConfig();

    private static String DEATH_BOOL_PATH = "Config.ShouldHaveDeathCounterInTab";


    public void updateScoreboard(Player player) {
        int deathsCountConfig = config.getInt("Deaths." + player.getUniqueId());
        if (config.getBoolean(DEATH_BOOL_PATH)){
            player.setPlayerListName(player.getName() + " §c" + deathsCountConfig);
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        updateScoreboard(player);
    }

    @EventHandler
    public void onPlayerDeaths(PlayerDeathEvent event){
        Player player = event.getEntity();

        int deaths = config.getInt("Deaths." + player.getUniqueId());
        deaths++;
        config.set("Deaths." + player.getUniqueId(), deaths);
        Main.getPlugin().saveConfig();
        updateScoreboard(player);
    }
}
