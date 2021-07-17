package me.destinyofyeet.CombinedMcPlugin.events;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class FlyWithoutElytra implements Listener {
    static HashMap<Player, Boolean> shouldTakeFallDmg = new HashMap<>();

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location worldSpawn = Bukkit.getWorld("world").getSpawnLocation();
        FileConfiguration config = Main.getPlugin().getConfig();
        double distance = config.getDouble("Config.DistanceOfSpawnToStillHaveElytra");
        if (!player.getWorld().equals(Bukkit.getWorld("world")))
            return;
        if (player.getFallDistance() >= 5 && player.getLocation().distanceSquared(worldSpawn) <= distance * distance){
            player.setGliding(true);
            shouldTakeFallDmg.put(player, false);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
            if (shouldTakeFallDmg.get(player) == null){
                event.setCancelled(false);
                return;
            }
            event.setCancelled(!shouldTakeFallDmg.get(player));
            shouldTakeFallDmg.put(player, true);
        }
    }
}

