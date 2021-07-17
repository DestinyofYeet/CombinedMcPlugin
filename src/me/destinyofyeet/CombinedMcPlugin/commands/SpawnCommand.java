package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Server;

import java.util.HashMap;
import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    static HashMap<Player, Integer> spawnMap = new HashMap<>();
    private Location location;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cDieser Command kann nur von Spielern verwendet werden!");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("CombinedMcPlugin.spawn.spawn")){
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }

        FileConfiguration config = Main.getPlugin().getConfig();

        String worldName = config.getString("Spawn.World");
        if (worldName == null){
            location = Bukkit.getWorlds().get(0).getSpawnLocation();
        }

        if (worldName != null) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                double x = config.getDouble("Spawn.X");
                double y = config.getDouble("Spawn.Y");
                double z = config.getDouble("Spawn.Z");

                location = new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());

            } else {
                double spawnX = Bukkit.getWorlds().get(0).getSpawnLocation().getX();
                double spawnY = Bukkit.getWorlds().get(0).getSpawnLocation().getY();
                double spawnZ = Bukkit.getWorlds().get(0).getSpawnLocation().getZ();
                location = new Location(Bukkit.getWorlds().get(0), spawnX, spawnY, spawnZ, player.getLocation().getYaw(), player.getLocation().getPitch());
            }
        }

        double playerX = player.getLocation().getX();
        double playerZ = player.getLocation().getZ();

        int taskID;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown <= 0){
                    player.teleport(location);
                    player.sendMessage("§aDu wurdest zum Spawn teleportiert!");
                    Bukkit.getScheduler().cancelTask(spawnMap.get(player));
                } else {
                    if (playerX != player.getLocation().getX() || playerZ != player.getLocation().getZ()){
                        Bukkit.getScheduler().cancelTask(spawnMap.get(player));
                        player.sendMessage("§cDu hast dich bewegt! Teleportation abgebrochen!");
                    } else {
                        player.sendMessage("§7Du wirst in §6" + countdown + "§7 sekunden teleportiert!");
                        countdown--;
                    }
                }

            }
        }, 0, 20);
        spawnMap.put(player, taskID);

        return true;
    }
}
