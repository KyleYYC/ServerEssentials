package me.kylesimmonds.serveressentials.players;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.ScoreboardAPI;
import me.kylesimmonds.serveressentials.ranks.Rank;
import me.kylesimmonds.serveressentials.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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

    public void displayRankBelowName(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("test", "test2");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getRanks().getString("Ranks." + ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank") + ".Prefix")));
        p.setScoreboard(board);
    }

    //TODO add placeholders/scrolling text
    public void showdefaultScoreboard(Player p) {
        if (Main.getPlugin().getConfig().getBoolean("Scoreboard.default.enabled")) {
            ScoreboardAPI api = new ScoreboardAPI(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Scoreboard.default.title")));

            //Limit for lines is 15
            for (int i = 1; i < 15; i++) {
                if (!(Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i) == null)) {
                    if ((Main.getPlugin().getConfig().getString("Scoreboard.default.line-") + i).isEmpty()) {
                        api.blankLine();
                    } else {
                        api.add(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i)));
                    }
                }
            }
            api.build();
            api.send(p);
        }
    }
}
