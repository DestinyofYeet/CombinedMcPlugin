package me.destinyofyeet.CombinedMcPlugin.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteCommand implements CommandExecutor, Listener{
	
	private ArrayList<UUID> mutedPlayers = new ArrayList<>();
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(mutedPlayers.contains(player.getUniqueId()) && !player.hasPermission("CombinedMcPlugin.mute.bypass")) {
			player.sendMessage("§cDu bist gemuted und kannst somit nicht schreiben!");
			event.setCancelled(true);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("CombinedMcPlugin.mute.mute")) {
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (!mutedPlayers.contains(target.getUniqueId())) {
						mutedPlayers.add(target.getUniqueId());
						sender.sendMessage("§aSpieler §6" + target.getName() + "§a wurde gemuted!");
						target.sendMessage("§cDu wurdest gemuted!");

					} else {

						mutedPlayers.remove(target.getUniqueId());
						sender.sendMessage("§aSpieler §6" + target.getName() + "§a wurde entmuted!");
						target.sendMessage("§aDu wurdest entmuted und kannst wieder schreiben!");
					}


				} else {
					sender.sendMessage("§cDer Spieler §6" + args[0] + " §cist nicht online!");
				}
				return true;

			} else {
				return false;
			}

		} else {
			sender.sendMessage("§cKeine Berechtigung!");
			return true;
		}
	}
	

}
