package me.kylesimmonds.serveressentials;


import me.kylesimmonds.serveressentials.commands.Balance;
import me.kylesimmonds.serveressentials.commands.List;
import me.kylesimmonds.serveressentials.commands.Spawns;
import me.kylesimmonds.serveressentials.events.JoinEvent;
import me.kylesimmonds.serveressentials.events.LoginEvent;
import me.kylesimmonds.serveressentials.events.QuitEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/*
Reccomended Plugins To use With SE:
- Anticheat

//Config Files:
config.yml
players.yml
economy.yml

Prefixes
No perms

Staff Tools

 */

public class Main extends JavaPlugin {

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.DARK_GRAY + "] ";
    public static String prefixWarn = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.RED + " WARN" + ChatColor.DARK_GRAY + "] ";
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

        loadConfig(); //Loads configuration file
        loadConfigManager(); //Loads custom configs

        MOTD.loadMOTD(); //Loads MOTD

        getCommand("spawn").setExecutor(spawn);
        getCommand("setspawn").setExecutor(spawn);
        getCommand("list").setExecutor(list);
        getCommand("bal").setExecutor(bal);

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);

        getServer().getConsoleSender().sendMessage(serverEnabled); //Enabled Server
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

    public static Main getPlugin() {
        return instance;
    }

}
