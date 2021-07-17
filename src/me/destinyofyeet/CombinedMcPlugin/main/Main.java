package me.destinyofyeet.CombinedMcPlugin.main;

import me.destinyofyeet.CombinedMcPlugin.commands.*;
import me.destinyofyeet.CombinedMcPlugin.events.*;
import me.destinyofyeet.CombinedMcPlugin.utils.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main CombinedMcPlugin;
    public static ScoreboardStuff scoreboardStuff;

    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        System.out.println("CombinedPlugin is now loading.");
        CombinedMcPlugin = this;

        scoreboardStuff = new ScoreboardStuff();
        MuteCommand muteCommand = new MuteCommand();

        if (!(Bukkit.getOnlinePlayers().isEmpty())){
            for (Player player: Bukkit.getOnlinePlayers())
                scoreboardStuff.updateScoreboard(player);
        }

        TabCompleterClass tabCompleterClass = new TabCompleterClass();

        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("rlconfig").setExecutor(new ReloadConfigCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(tabCompleterClass);
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("delhome").setTabCompleter(tabCompleterClass);
        getCommand("listhome").setExecutor(new ListHomeCommand());
        getCommand("skipnight").setExecutor(new EnableNightSkipCommand());
        getCommand("skipnight").setTabCompleter(tabCompleterClass);
        getCommand("msg").setExecutor(new msgCommand());
        getCommand("msg").setTabCompleter(tabCompleterClass);
        getCommand("msgspy").setExecutor(new msgSpyCommand());
        getCommand("msgspy").setTabCompleter(tabCompleterClass);
        getCommand("mute").setExecutor(muteCommand);
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("reply").setTabCompleter(tabCompleterClass);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SkipNightByHalfPlayers(), this);
        pluginManager.registerEvents(new FlyWithoutElytra(), this);
        pluginManager.registerEvents(scoreboardStuff, this);
        pluginManager.registerEvents(muteCommand, this);


        config.options().header("DistanceOfSpawnToSillHaveElytra: Distance from world spawn to still have the elytra.\n" +
                "ShouldHaveDeathCounterInTab: DeathCounter: ja/nein: true/false.\n" +
                "ShouldSkipNight: Nacht überspringen feature: ja/nein: true/false.\n" +
                "msgspy: msgspy: ja/nein: true/false. Kann auch ingame gemacht werden mit /msgspy enable/disable\n" +
                "maxMobs: Maximale Anzahl an mobs per dimension. -1 Um es auszuschalten\n" +
                "maxNonOpHomes: Maximale Anzahl an homes ohne OP. Für permissions plugin usw: Permission Node: CombinedMcPlugin.home.sethome.more");

        config.addDefault("Config.DistanceOfSpawnToStillHaveElytra", 100D);
        config.addDefault("Config.ShouldHaveDeathCounterInTab", true);
        config.addDefault("Config.ShouldSkipNight", true);
        config.addDefault("Config.msgSpy", false);
        config.addDefault("Config.maxMobs", -1);
        config.addDefault("Config.maxNonOpHomes", 1);

        config.options().copyDefaults(true);
        config.options().copyHeader(true);
        this.saveConfig();

        int pluginID = 9702;
        Metrics metrics = new Metrics(this, pluginID);

        if (!(config.getInt("Config.maxMobs") == -1)) {
            pluginManager.registerEvents(new MobLimiterStuff(), this);
        } else {
            System.out.println("Mobs werden nicht limitert!");
        }

        System.out.println("CombinedPlugin has finished setup.");
    }

    public static Main getPlugin(){
        return CombinedMcPlugin;
    }
}
