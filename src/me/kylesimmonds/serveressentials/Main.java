package me.kylesimmonds.serveressentials;


import me.kylesimmonds.serveressentials.commands.Spawns;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/*

Prefixes
No perms

 */

public class Main extends JavaPlugin {

    PluginDescriptionFile pdf = this.getDescription();

    //Register Commands:
    private Spawns spawn = new Spawns();

    private static Main instance;
    public void onEnable() {
        instance = this;
        getServer().getConsoleSender().sendMessage(
                ChatColor.DARK_RED + "\n----------------------" +
                ChatColor.RED + "\nServer Essentials Enabled" +
                ChatColor.DARK_RED + "\n----------------------" +
                ChatColor.GREEN + "\nVersion: " + ChatColor.GOLD + pdf.getVersion() +
                ChatColor.GREEN + "\nAuthor: " + ChatColor.GOLD + pdf.getAuthors().toString());

        getCommand("spawn").setExecutor(spawn);
        getCommand("setspawn").setExecutor(spawn);

        loadConfig(); //Loads configuration file
    }


    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nServer Essentials Disabled");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public static Main getPlugin(){
        return instance;
    }

}
