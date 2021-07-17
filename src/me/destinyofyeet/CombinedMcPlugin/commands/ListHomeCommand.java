package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ListHomeCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.home.listhome")){
            sender.sendMessage("§cKeine Berechtigung!");
            return true;
        }
        if (args.length < 1){
            if (sender instanceof Player){
                Player player = (Player) sender;
                String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
                List<String> homeList = config.getStringList(pathToPlayerConfig + ".list");
                if (!homeList.isEmpty())
                    player.sendMessage("§aDu hast folgende homes:\n§3" + String.join(" ", homeList));
                else player.sendMessage("§cDu hast keine homes!");
            } else {
                sender.sendMessage("§cDu musst ein Spieler sein um die eigenen homes zu sehen!");
            }
            return true;
        } else if (args.length == 1){
            if (!sender.hasPermission("CombinedMcPlugin.home.listhome.others")){
                sender.sendMessage("§cKeine Berechtigung!");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (!Bukkit.getOnlinePlayers().contains(player)){
                sender.sendMessage("§cDer Spieler §6" + args[0] + "§c ist nicht online!");
                return true;
            }
            String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
            List<String> homeList = config.getStringList(pathToPlayerConfig + ".list");
            if (homeList.isEmpty()){
                sender.sendMessage("§cDer Spieler §6" + player.getName() + "§c hat keine homes!");
            } else {
                if (sender instanceof Player)
                    sender.sendMessage("§aDer Spieler §6" + player.getName() + "§a hat folgende homes:\n§3" + String.join(", ", homeList));
                else sender.sendMessage("§aDer Spieler §6" + player.getName() + "§a hat folgende homes:§3 " + String.join(", ", homeList));
            }
            return true;
        } else
            return false;
    }
}
