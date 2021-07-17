package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class msgCommand implements CommandExecutor {
    final FileConfiguration config = Main.getPlugin().getConfig();

    private static HashMap<UUID, UUID> fromTo = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.msg.send")){
            sender.sendMessage("§cKeine Berechtigung!");
            return true;
        }
        if (args.length < 1)
            return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (!Bukkit.getOnlinePlayers().contains(target)){
            sender.sendMessage("§cDer Spieler §6" + args[0] + "§c ist nicht online!");
            return true;
        }

        String message = "";
        for(int i = 1; i != args.length; i++)

            message += args[i] + " ";

        if (message.isEmpty()){
            return true;
        }

        String messageSent = "§6" + sender.getName() + "§b --> §6" + target.getName() + "§r§7: " + message;

        if (sender instanceof Player){
            Player senderPlayer = (Player) sender;
            fromTo.put(target.getUniqueId(), senderPlayer.getUniqueId());
        }


        target.sendMessage(messageSent);
        sender.sendMessage(messageSent);

        if (config.getBoolean("Config.msgSpy")){
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.hasPermission("CombinedMcPlugin.msg.spy") && (player != target || sender != player)){
                    player.sendMessage(messageSent);
                }
            }
        }

        return true;
    }

    public static HashMap<UUID, UUID> getFromTo() {
        return fromTo;
    }
}
