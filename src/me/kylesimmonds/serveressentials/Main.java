package me.kylesimmonds.serveressentials;


import me.kylesimmonds.serveressentials.commands.*;
import me.kylesimmonds.serveressentials.events.ChatFormat;
import me.kylesimmonds.serveressentials.events.JoinEvent;
import me.kylesimmonds.serveressentials.events.LoginEvent;
import me.kylesimmonds.serveressentials.events.QuitEvent;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import me.kylesimmonds.serveressentials.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/*
Recommended Plugins To use With SE:
- Anti-cheat

//Config Files:
config.yml
players.yml
economy.yml
statistics.yml
ranks.yml
plugin.yml
motd.txt

Dev TODO:
perms
Staff Tools/Bans
Kits
Cheats Commands


Permission Structure:

serveressentials.staff
serveressentials.command
serveressentials.admin
 */

//VERSION 1.0.5 - 2020/06/25

public class Main extends JavaPlugin {

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.DARK_GRAY + "] ";
    public static String prefixWarn = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.RED + " WARN" + ChatColor.DARK_GRAY + "] ";
    public static String prefixDebug = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "SE" + ChatColor.RED + " DEBUG" + ChatColor.DARK_GRAY + "] ";

    PluginDescriptionFile pdf = this.getDescription();

    private String github = "github.com/KyleYYC/ServerEssentials";
    private String serverEnabled = prefix + ChatColor.GRAY + "Loading..." + ChatColor.DARK_RED + "\n----------------------" + ChatColor.RED + "\nServer Essentials Enabled" + ChatColor.DARK_RED + "\n----------------------" + ChatColor.GREEN + "\nVersion: " + ChatColor.GOLD + pdf.getVersion() + ChatColor.GREEN + "\nAuthor: " + ChatColor.GOLD + pdf.getAuthors().toString() + ChatColor.LIGHT_PURPLE + "\n\nGithub: " + ChatColor.BLUE + github;
    private String serverDisabled = prefix + ChatColor.GRAY + "Disabling..." + ChatColor.DARK_RED + "\n----------------------" + ChatColor.RED + "\nServer Essentials Disabled" + ChatColor.DARK_RED + "\n----------------------" + ChatColor.GREEN + "\nVersion: " + ChatColor.GOLD + pdf.getVersion() + ChatColor.GREEN + "\nAuthor: " + ChatColor.GOLD + pdf.getAuthors().toString() + ChatColor.LIGHT_PURPLE + "\n\nGithub: " + ChatColor.BLUE + github;

    private static Main instance;

    private Spawns spawn = new Spawns();
    private List list = new List();
    private Balance bal = new Balance();
    private RankCmd rankcmd = new RankCmd();
    private Warp warpCmd = new Warp();
    private Nickname nickCmd = new Nickname();
    private Speed speedCmd = new Speed();


    public void onEnable() {
        instance = this;

        loadDebugMode();
        loadConfig();
        loadConfigManager();

        //Loads players.yml & player stats
        PlayerFunctions pf = new PlayerFunctions();
        pf.loadPlayers();

        //Loads MOTD
        MOTD motd = new MOTD();
        motd.loadMOTD();

        //Loads ranks.yml
        loadRanks();

        //Register Commands
        getCommand("spawn").setExecutor(spawn);
        getCommand("setspawn").setExecutor(spawn);
        getCommand("list").setExecutor(list);
        getCommand("bal").setExecutor(bal);
        getCommand("rank").setExecutor(rankcmd);
        getCommand("warp").setExecutor(warpCmd);
        getCommand("nick").setExecutor(nickCmd);
        getCommand("speed").setExecutor(speedCmd);

        //Register Events
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatFormat(), this);

        getServer().getConsoleSender().sendMessage(serverEnabled);
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(serverDisabled);
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

        //ranks.yml
        ConfigManager.getInstance().saveRanks();
    }

    private void loadDebugMode() {
        if (getConfig().getBoolean("debug-mode")) {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.DARK_RED + "\n\nDEBUG MODE ENABLED: " + ChatColor.RED + "\nTurn this setting off in the " + ChatColor.YELLOW + "config.yml\n" + ChatColor.DARK_RED + ChatColor.STRIKETHROUGH + "---------------------------------------------------------");
        }
        Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "Debug mode is" + ChatColor.RED + " disabled.");
    }

    private void loadRanks() {
        if (ConfigManager.getInstance().getRanks().getConfigurationSection("Ranks") == null) {
            RankManager rm = new RankManager();
            rm.applyRankDefault();
        }
    }

    public static Main getPlugin() {
        return instance;
    }

}
