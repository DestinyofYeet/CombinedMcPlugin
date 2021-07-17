package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EnableNightSkipCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.night.manage")){
            sender.sendMessage("§cKeine Berechtigung!");
            return true;
        }
        boolean isEnabled = config.getBoolean("Config.ShouldSkipNight");
        if (args.length > 1){
            return false;
        } else if(args.length < 1){
            if (isEnabled){
                sender.sendMessage("§aDas Nacht-Skip feature ist gerade §6Aktiv§a!");
            } else {
                sender.sendMessage("§aDas Nacht-Skip feature ist gerade §6Inaktiv§a!");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("enable")){
            if (isEnabled){
                sender.sendMessage("§cDas Nacht-Skip feature ist schon §6Aktiv§c!");
            } else {
                config.set("Config.ShouldSkipNight", true);
                sender.sendMessage("§aDas Nacht-Skip feature wurde §6Aktiviert§a!");
            }
            Main.getPlugin().saveConfig();
            return true;
        } else if (args[0].equalsIgnoreCase("disable")){
            if (!isEnabled){
                sender.sendMessage("§cDas Nacht-Skip feature ist schon §6Inaktiv§c!");
            } else {
                config.set("Config.ShouldSkipNight", false);
                sender.sendMessage("§aDas Nacht-Skip feature wurde §6Deaktiviert§a!");
            }
            Main.getPlugin().saveConfig();
            return true;
        } else {
            return false;
        }
    }
}
