package me.destinyofyeet.CombinedMcPlugin.commands;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class ReloadConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("CombinedMcPlugin.config.reload")){
            sender.sendMessage("§cKeine Berechtigung");
            return true;
        }

        Main.getPlugin().reloadConfig();

        sender.sendMessage("§aConfig wurde neu geladen!");
        return true;
    }
}
