package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class msgSpyCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.msg.spy")){
            sender.sendMessage("§cKeine Berechtigung!");
            return true;
        }
        boolean isEnabled = config.getBoolean("Config.msgSpy");
        if (args.length < 1){
            if (isEnabled){
                sender.sendMessage("§aMsgSpy ist §6Aktiv§a!");
            } else {
                sender.sendMessage("§aMsgSpy ist §6Deaktiviert§a!");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("enable")) {
            if (isEnabled) {
                sender.sendMessage("§cMsgSpy ist schon §6Aktiv§a!");
            } else {
                config.set("Config.msgSpy", true);
                Main.getPlugin().saveConfig();
                sender.sendMessage("§aMsgSpy ist jetzt §6Aktiviert§a!");
            }
            return true;
        } else if(args[0].equalsIgnoreCase("disable")){
            if (!isEnabled){
                sender.sendMessage("§cMsgSpy ist schon §6Inaktiv§a!");
            } else {
                config.set("Config.msgSpy", false);
                Main.getPlugin().saveConfig();
                sender.sendMessage("§aMsgSpy ist jetzt §6Deaktiviert§a!");
            }
            return true;
        } else
            return false;
    }
}
