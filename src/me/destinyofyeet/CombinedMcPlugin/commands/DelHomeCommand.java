package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class DelHomeCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cDieser Command kann nur von Spielern verwendet werden!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("CombinedMcPlugin.home.delhome")){
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }
        String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
        List<String> homeList = config.getStringList(pathToPlayerConfig + ".list");

        if (args.length < 1){
            player.sendMessage("§3Du hast folgende home(s):\n" + String.join(", ", homeList));
            return true;
        } else if (homeList.contains(args[0])){
            homeList.remove(args[0]);
            config.set(pathToPlayerConfig + ".list", homeList);
            player.sendMessage("§aErfolgreich home §6" + args[0] + "§a gelöscht!");
        }
        Main.getPlugin().saveConfig();

        return true;
    }
}
