package me.destinyofyeet.CombinedMcPlugin.events;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SkipNightByHalfPlayers implements Listener {
    static ArrayList<Player> sleepingPlayer = new ArrayList<Player>();
    FileConfiguration config = Main.getPlugin().getConfig();

    private boolean day() {
        long time = Bukkit.getWorld("world").getTime();

        Bukkit.getWorld("world").setStorm(false);
        int duration = (300 + new Random().nextInt(600)) * 20;
        Bukkit.getWorlds().get(0).setThundering(false);
        Bukkit.getWorlds().get(0).setClearWeatherDuration(duration);
        return time > 0 && time < 12010;
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event){
        if (!config.getBoolean("Config.ShouldSkipNight")){
            return;
        }
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)){
            Player player = event.getPlayer();
            player.setSleepingIgnored(true);
            sleepingPlayer.add(player);
            if (sleepingPlayer.size() >= (Bukkit.getWorld("world").getPlayers().size() / 2)){
                player.setSleepingIgnored(false);
                if (sleepingPlayer.size() == 1)
                    Bukkit.broadcastMessage("§6" + sleepingPlayer.size() + "§a schläft... Nacht wird übersprungen.");
                else
                    Bukkit.broadcastMessage("§6" + sleepingPlayer.size() + "§a schlafen... Nacht wird übersprungen.");
                sleepingPlayer.clear();
                Bukkit.getWorld("world").setTime(500);
            } else {
                Bukkit.broadcastMessage("§6" + sleepingPlayer.size() + "§a/§6" + Bukkit.getWorld("world").getPlayers().size() / 2 + "§a schlafen... §6");
            }
        }
    }

    @EventHandler
    public void onPLayerBedLeaveEvent(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();
        player.setSleepingIgnored(false);
        sleepingPlayer.remove(player);
        if (!config.getBoolean("Config.ShouldSkipNight")){
            return;
        }
        if (!day())
            Bukkit.broadcastMessage("§6" + sleepingPlayer.size() + "§a/§6" + Bukkit.getWorld("world").getPlayers().size() / 2 + "§a schlafen... §6");
    }

}
