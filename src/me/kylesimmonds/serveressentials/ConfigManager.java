package me.kylesimmonds.serveressentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {

    /*

    players.yml -- Track all user stats/data
    economy.yml -- Economy Stats/Item Prices

     */

    //Configs:
    public FileConfiguration playersCfg;
    public File playersFile;

    public FileConfiguration economyCfg;
    public File economyFile;

    public FileConfiguration statisticsCfg;
    public File statisticsFile;

    private static ConfigManager instance;
    Main plugin;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void setup(Main plugin) {
        this.plugin = plugin;
        if (!Main.getPlugin().getDataFolder().exists()) {
            Main.getPlugin().getDataFolder().mkdir();
        }

        playersFile = new File(Main.getPlugin().getDataFolder(), "players.yml");
        economyFile = new File(Main.getPlugin().getDataFolder(), "economy.yml");
        statisticsFile = new File(Main.getPlugin().getDataFolder(), "statistics.yml");

        //Players File:
        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.AQUA + "players.yml" + ChatColor.GREEN + " has been created.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "players.yml" + ChatColor.RED + " could not be created.");
            }
        }

        //Economy File:
        if (!economyFile.exists()) {
            try {
                economyFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.AQUA + "economy.yml" + ChatColor.GREEN + " has been created.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "economy.yml" + ChatColor.RED + " could not be created.");
            }
        }

        //Statistics File:
        if (!statisticsFile.exists()) {
            try {
                statisticsFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.AQUA + "statistics.yml" + ChatColor.GREEN + " has been created.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "statistics.yml" + ChatColor.RED + " could not be created.");
            }
        }

        playersCfg = YamlConfiguration.loadConfiguration(playersFile);
        economyCfg = YamlConfiguration.loadConfiguration(economyFile);
        statisticsCfg = YamlConfiguration.loadConfiguration(statisticsFile);
    }

    public FileConfiguration getPlayers() {
        return playersCfg;
    }

    public FileConfiguration getEconomy() {
        return economyCfg;
    }

    public FileConfiguration getStatistics() {
        return statisticsCfg;
    }

    //Saving
    public void savePlayers() {
        try {
            playersCfg.save(playersFile);
            playersCfg = YamlConfiguration.loadConfiguration(playersFile);
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "players.yml" + ChatColor.GREEN + " file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "players.yml" + ChatColor.RED + " could not be saved");
        }
    }

    public void saveEconomy() {
        try {
            economyCfg.save(economyFile);
            economyCfg = YamlConfiguration.loadConfiguration(economyFile);
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "economy.yml" + ChatColor.GREEN + " file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "economy.yml" + ChatColor.RED + " could not be saved");
        }
    }

    public void saveStatistics() {
        try {
            statisticsCfg.save(statisticsFile);
            statisticsCfg = YamlConfiguration.loadConfiguration(statisticsFile);
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "statistics.yml" + ChatColor.GREEN + " file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "statistics.yml" + ChatColor.RED + " could not be saved");
        }
    }

    //TODO FIX
    public void applyDefaults() {
        //Economy
        Reader defConfigStream = null;
        defConfigStream = new InputStreamReader(plugin.getResource("economy.yml"), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            economyCfg.setDefaults(defConfig);
            saveEconomy();
        }

        //Players
        Reader defConfigStreamP = null;
        defConfigStreamP = new InputStreamReader(plugin.getResource("players.yml"), StandardCharsets.UTF_8);
        if (defConfigStreamP != null) {
            YamlConfiguration defConfigP = YamlConfiguration.loadConfiguration(defConfigStreamP);
            playersCfg.setDefaults(defConfigP);
            savePlayers();
        }

        Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "Successfully applied default values to all files.");

    }

}
