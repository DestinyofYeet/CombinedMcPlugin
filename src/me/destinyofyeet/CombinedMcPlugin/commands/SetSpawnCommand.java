package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cDieser Command kann nur von Spielern genutzt werden!");
            return true;
        }

        Player player = (Player) sender;

        if (!(player.hasPermission("CombinedMcPlugin.spawn.set"))){
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }


        FileConfiguration config = Main.getPlugin().getConfig();
        config.set("Spawn.World", player.getWorld().getName());
        config.set("Spawn.X", player.getLocation().getX());
        config.set("Spawn.Y", player.getLocation().getY());
        config.set("Spawn.Z", player.getLocation().getZ());
        Main.getPlugin().saveConfig();
        player.sendMessage("§aPunkt für /spawn gesetzt!");

        return true;
    }
}
