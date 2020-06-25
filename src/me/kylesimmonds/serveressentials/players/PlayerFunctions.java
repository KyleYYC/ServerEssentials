package me.kylesimmonds.serveressentials.players;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.ranks.Rank;
import me.kylesimmonds.serveressentials.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;


public class PlayerFunctions {
    public ArrayList<SEPlayer> playerList = new ArrayList<>();

    public void loadPlayers() {
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        // Check if players.yml has any players, if not send console message
        if (ConfigManager.getInstance().getPlayers().getConfigurationSection("Player") == null) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GOLD + "No players found in " + ChatColor.YELLOW + "players.yml");
        } else {// Load all players from players.yml into a SEPlayer object
            int i = 0;
            for (String u : ConfigManager.getInstance().getPlayers().getConfigurationSection("Player").getKeys(false)) {
                Player pl = Bukkit.getPlayer(UUID.fromString(u));
                RankManager rm = new RankManager();
                Rank rank = rm.getPlayerRank(u);
                SEPlayer sePlayer = new SEPlayer(u, pl.getName(), rank, ConfigManager.getInstance().getPlayers().getInt("Player." + pl.getUniqueId().toString() + ".Balance"));
                playerList.add(sePlayer);
                i++;

                if (Main.getPlugin().getConfig().getBoolean("debug-mode")) {
                    Bukkit.getConsoleSender().sendMessage(Main.prefixDebug + ChatColor.GREEN + "Registered " + ChatColor.YELLOW + pl.getName() + ChatColor.LIGHT_PURPLE + " #" + myFormat.format(i));
                }
            }
            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.LIGHT_PURPLE + myFormat.format(i) + ChatColor.GREEN + " players have successfully been loaded from " + ChatColor.YELLOW + "players.yml");
        }
    }

    public void refreshPlayerList() {
        playerList.clear();
        loadPlayers();
        Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.AQUA + "Refreshed " + ChatColor.YELLOW + "players.yml" + ChatColor.AQUA + ".");
    }
}
