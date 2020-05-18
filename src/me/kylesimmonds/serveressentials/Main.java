package me.kylesimmonds.serveressentials;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    PluginDescriptionFile pdf = this.getDescription();
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(
                ChatColor.DARK_RED + "\n----------------------" +
                ChatColor.RED + "\nServer Essentials Enabled" +
                ChatColor.DARK_RED + "\n----------------------" +
                ChatColor.GREEN + "\nVersion: " + ChatColor.GOLD + pdf.getVersion().toString() +
                ChatColor.GREEN + "\nAuthor: " + ChatColor.GOLD + pdf.getAuthors().toString());
    }


    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nServer Essentials Disabled");
    }
}
