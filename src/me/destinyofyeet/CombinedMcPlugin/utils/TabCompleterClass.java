package me.destinyofyeet.CombinedMcPlugin.utils;

import me.destinyofyeet.CombinedMcPlugin.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleterClass implements TabCompleter {
    final FileConfiguration config = Main.getPlugin().getConfig();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("home") && args.length > 0){
            if (!(sender instanceof Player))
                return null;
            Player player = (Player) sender;
            String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
            return (List<String>) config.getList(pathToPlayerConfig + ".list");

        } else if (command.getName().equalsIgnoreCase("delhome") && args.length > 0){
            if (!(sender instanceof Player))
                return null;
            Player player = (Player) sender;
            String pathToPlayerConfig = "Home." + player.getUniqueId().toString();
            return (List<String>) config.getList(pathToPlayerConfig + ".list");

        } else if (command.getName().equalsIgnoreCase("skipnight") && args.length > 0) {
            List<String> values = new ArrayList() {{
                add("enable");
                add("disable");
            }};
            return values;

        } else if (command.getName().equalsIgnoreCase("msgspy") && args.length > 0){
            List<String> values = new ArrayList(){{
               add("enable");
               add("disable");
            }};
            return values;

        } else if (command.getName().equalsIgnoreCase("msg") && args.length > 1){
            List<String> values = new ArrayList(){{
                add("");
            }};
            return values;

        } else if (command.getName().equalsIgnoreCase("reply") && args.length > 0){
            return new ArrayList<String>(){{add("");}};
        }
        return null;
    }
}
