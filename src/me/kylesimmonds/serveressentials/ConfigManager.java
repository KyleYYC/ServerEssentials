package me.kylesimmonds.serveressentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    //Files & Configs Here
    public FileConfiguration playerscfg;
    public File playersfile;

    public void setup() {
        //Check to see if plugin folder exists
        if (!Main.getPlugin().getDataFolder().exists()) {
            Main.getPlugin().getDataFolder().mkdir();
        }

        playersfile = new File(Main.getPlugin().getDataFolder(), "players.yml");

        if (!playersfile.exists()) {
            try {
                playersfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "The players.yml file has been created");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.RED + "Could not create the players.yml file.");
            }
        }


        playerscfg = YamlConfiguration.loadConfiguration(playersfile);

        Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "Setup configuration files complete");
    }

    //Allows access from players file.
    public FileConfiguration getPlayers() {
        return playerscfg;
    }

    //Saves players file
    public void savePlayers() {
        try {
            playerscfg.save(playersfile);
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "The players.yml file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.RED + "Could not save the players.yml file.");
        }
    }

    public void reloadPlayers() {
        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
        Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "The players.yml file has been reloaded");
    }

}
