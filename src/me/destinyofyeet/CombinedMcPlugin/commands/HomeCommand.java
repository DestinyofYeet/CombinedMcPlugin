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

import java.util.HashMap;
import java.util.List;

public class HomeCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();
    private HashMap<Player, Integer> tpMap = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cDieser Command kann nur von Spielern genutzt werden!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("CombinedMcPlugin.home.home")){
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }

        String homeName = "";
        if (args.length < 1){
            homeName = "home";
        } else if (args.length == 1){
            homeName = args[0];
        } else
            return false;

        String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
        List<String> homeList = (List<String>) config.getList(pathToPlayerConfig + ".list");
        if (homeList == null || !homeList.contains(homeName)){
            player.sendMessage("§cDu hast kein home mit dem Namen §6" + homeName + "§c!");
            return true;
        }
        String pathToHome = pathToPlayerConfig + "." + homeName;

        double x = config.getDouble(pathToHome + ".x");
        double y = config.getDouble(pathToHome + ".y");
        double z = config.getDouble(pathToHome + ".z");
        World world = Bukkit.getWorld(config.getString(pathToHome + ".world"));

        Location location = new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());

        double playerX = player.getLocation().getX();
        double playerZ = player.getLocation().getZ();

        int taskID;
        String finalHomeName = homeName;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown <= 0){
                    player.teleport(location);
                    player.sendMessage("§aErfolgreich zu home §6" + finalHomeName + " §ateleportiert!");
                    Bukkit.getScheduler().cancelTask(tpMap.get(player));
                } else {
                    if (playerX != player.getLocation().getX() || playerZ != player.getLocation().getZ()){
                        Bukkit.getScheduler().cancelTask(tpMap.get(player));
                        player.sendMessage("§cDu hast dich bewegt! Teleportation abgebrochen!");
                    } else {
                        player.sendMessage("§7Du wirst in §6" + countdown + "§7 sekunden teleportiert!");
                        countdown--;
                    }
                }

            }
        }, 0, 20);
        tpMap.put(player, taskID);
        return true;
    }
}
