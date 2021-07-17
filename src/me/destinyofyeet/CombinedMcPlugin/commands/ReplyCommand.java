package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.msg.reply")){
            sender.sendMessage("§cKeine Berechtigung!");
            return true;
        }

        if (!(sender instanceof Player)){
            sender.sendMessage("§cDieser Command kann nur von Spielern verwendet werden!");
            return true;
        }

        Player player = (Player) sender;

        if (!msgCommand.getFromTo().containsKey(player.getUniqueId())){
            player.sendMessage("§cDu hast mit keinem Spieler geschrieben!");
            return true;
        }

        UUID targetId = msgCommand.getFromTo().get(player.getUniqueId());

        Player target = Bukkit.getPlayer(targetId);

        StringBuilder message = new StringBuilder();
        for(int i = 0; i != args.length; i++)
            message.append(args[i]).append(" ");

        String messageSent = "§6" + sender.getName() + "§b --> §6" + target.getName() + "§r§7: " + message.toString();

        player.sendMessage(messageSent);
        target.sendMessage(messageSent);

        msgCommand.getFromTo().put(targetId, player.getUniqueId());

        return true;
    }
}
