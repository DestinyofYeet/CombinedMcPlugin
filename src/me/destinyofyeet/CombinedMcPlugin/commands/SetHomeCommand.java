package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetHomeCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Command kann nur von Spielern verwendet werden!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("CombinedMcPlugin.home.sethome")) {
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }

        String pathToPlayerConfig = "Home." + player.getUniqueId().toString();

        List<String> homeLists = config.getStringList(pathToPlayerConfig + ".list");

        String homeName = "";
        if (args.length < 1 && !homeLists.contains("home")) {
            homeName = "home";
        } else{
            homeName = args[0];
        }

        if (!player.hasPermission("CombinedMcPlugin.home.sethome.more") && !homeLists.contains(homeName)) {
            if (homeLists.size() >= config.getInt("Config.maxNonOpHomes")) {
                player.sendMessage("§cDu hast die maximale anzahl an homes erreicht, bitte lösche eins und versuche es erneut!");
                return true;

            }
        }
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        World world = player.getWorld();
        if (!homeLists.contains(homeName))
            homeLists.add(homeName);
        String pathToHome = pathToPlayerConfig + "." + homeName;
        config.set(pathToPlayerConfig + ".list", homeLists);
        config.set(pathToHome + ".x", x);
        config.set(pathToHome + ".y", y);
        config.set(pathToHome + ".z", z);
        config.set(pathToHome + ".world", world.getName());
        player.sendMessage("§aHome §6" + homeName + "§a has been set!");
        Main.getPlugin().saveConfig();
        return true;
    }
}