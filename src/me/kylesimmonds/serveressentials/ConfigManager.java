package me.kylesimmonds.serveressentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    //Configs:
    public FileConfiguration playersCfg;
    public File playersFile;

    public FileConfiguration economyCfg;
    public File economyFile;

    public FileConfiguration statisticsCfg;
    public File statisticsFile;

    public FileConfiguration ranksCfg;
    public File ranksFile;

    private static ConfigManager instance;
    Main plugin;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /***
     * Sets up all .yml files for Server Essentials
     * @param plugin - Instance of main class
     */
    public void setup(Main plugin) {
        this.plugin = plugin;
        if (!Main.getPlugin().getDataFolder().exists()) {
            Main.getPlugin().getDataFolder().mkdir();
        }

        playersFile = new File(Main.getPlugin().getDataFolder(), "players.yml");
        economyFile = new File(Main.getPlugin().getDataFolder(), "economy.yml");
        statisticsFile = new File(Main.getPlugin().getDataFolder(), "statistics.yml");
        ranksFile = new File(Main.getPlugin().getDataFolder(), "ranks.yml");


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

        //Ranks File:
        if (!ranksFile.exists()) {
            try {
                ranksFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.AQUA + "ranks.yml" + ChatColor.GREEN + " has been created.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "ranks.yml" + ChatColor.RED + " could not be created.");
            }
        }

        playersCfg = YamlConfiguration.loadConfiguration(playersFile);
        economyCfg = YamlConfiguration.loadConfiguration(economyFile);
        statisticsCfg = YamlConfiguration.loadConfiguration(statisticsFile);
        ranksCfg = YamlConfiguration.loadConfiguration(ranksFile);
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

    public FileConfiguration getRanks() {
        return ranksCfg;
    }

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

    public void saveRanks() {
        try {
            ranksCfg.save(ranksFile);
            ranksCfg = YamlConfiguration.loadConfiguration(ranksFile);
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "ranks.yml" + ChatColor.GREEN + " file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + "ranks.yml" + ChatColor.RED + " could not be saved");
        }
    }
}
