package me.destinyofyeet.CombinedMcPlugin.events;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobLimiterStuff implements Listener {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getLivingEntities().size() > config.getInt("Config.maxMobs")) {
                event.setCancelled(true);
        }
    }
}
