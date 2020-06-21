package me.kylesimmonds.serveressentials;


import me.kylesimmonds.serveressentials.commands.Balance;
import me.kylesimmonds.serveressentials.commands.List;
import me.kylesimmonds.serveressentials.commands.Spawns;
import me.kylesimmonds.serveressentials.events.JoinEvent;
import me.kylesimmonds.serveressentials.events.LoginEvent;
import me.kylesimmonds.serveressentials.events.QuitEvent;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/*
Reccomended Plugins To use With SE:
- Anti-cheat

//Config Files:
config.yml
players.yml
economy.yml
statistics.yml

Prefixes/Ranks
perms



Staff Tools

 */

//VERSION 1.0.2

public class Main extends JavaPlugin {

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.DARK_GRAY + "] ";
    public static String prefixWarn = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.RED + " WARN" + ChatColor.DARK_GRAY + "] ";
    public static String prefixDebug = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.RED + " DEBUG" + ChatColor.DARK_GRAY + "] ";
    public static String noPermission = ChatColor.RED + "Access Denied";

    PluginDescriptionFile pdf = this.getDescription();

    private String github = "github.com/KyleYYC/ServerEssentials";
    private String serverEnabled = prefix + ChatColor.GRAY + "Loading..." + ChatColor.DARK_RED + "\n----------------------" + ChatColor.RED + "\nServer Essentials Enabled" + ChatColor.DARK_RED + "\n----------------------" + ChatColor.GREEN + "\nVersion: " + ChatColor.GOLD + pdf.getVersion() + ChatColor.GREEN + "\nAuthor: " + ChatColor.GOLD + pdf.getAuthors().toString() + ChatColor.LIGHT_PURPLE + "\n\nGithub: " + ChatColor.BLUE + github;

    private static Main instance;

    //Register Commands:
    private Spawns spawn = new Spawns();
    private List list = new List();
    private Balance bal = new Balance();

    public void onEnable() {
        instance = this;

        loadDebugMode();
        loadConfig(); //Loads configuration file
        loadConfigManager(); //Loads custom configs

        PlayerFunctions.loadPlayers();

        MOTD.loadMOTD(); //Loads MOTD

        getCommand("spawn").setExecutor(spawn);
        getCommand("setspawn").setExecutor(spawn);
        getCommand("list").setExecutor(list);
        getCommand("bal").setExecutor(bal);

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);

        getServer().getConsoleSender().sendMessage(serverEnabled); //Enabled Server

        if (getConfig().getConfigurationSection("Spawn") == null) {
            getServer().getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "Please set the spawn using " + ChatColor.LIGHT_PURPLE + "/setspawn" + ChatColor.YELLOW + " in-game.");
        }
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nServer Essentials Disabled");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadConfigManager() {
        ConfigManager.getInstance().setup(this);

        //players.yml
        ConfigManager.getInstance().savePlayers();

        //economy.yml
        ConfigManager.getInstance().saveEconomy();

        //statistics.yml
        ConfigManager.getInstance().saveStatistics();
    }

    private void loadDebugMode() {
        if (getConfig().getBoolean("debug-mode")) {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.DARK_RED + "\n\nDEBUG MODE ENABLED: " + ChatColor.RED + "\nTurn this setting off in the " + ChatColor.YELLOW + "config.yml\n" + ChatColor.DARK_RED + ChatColor.STRIKETHROUGH + "---------------------------------------------------------");
        }
        Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "Debug mode is" + ChatColor.RED + " disabled.");
    }


    public static Main getPlugin() {
        return instance;
    }

}
