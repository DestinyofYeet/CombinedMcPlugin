package me.destinyofyeet.CombinedMcPlugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cDu musst ein Spieler sein um deinen Ping abzurufen!");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("CombinedMcPlugin.ping")){
            player.sendMessage("§cKeine Berechtigung!");
            return true;
        }

        Object mmsPlayer = null;

        try {
            mmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {}

        Field fieldPing = null;

        try {
            fieldPing = mmsPlayer.getClass().getDeclaredField("ping");
        } catch (NoSuchFieldException ignored) {}

        if (fieldPing == null){
            player.sendMessage("§cCommand ist gerade für die 1.17 nicht verfügbar!");
            return true;
        }


        fieldPing.setAccessible(true);
        try {
            player.sendMessage("§aDein Ping ist: §6" + fieldPing.getInt(mmsPlayer) + "§ams!");
        } catch (IllegalAccessException ignored) {}
        return true;
    }
}
