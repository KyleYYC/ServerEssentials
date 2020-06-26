package me.kylesimmonds.serveressentials.players;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.ScoreboardAPI;
import me.kylesimmonds.serveressentials.Scroll;
import me.kylesimmonds.serveressentials.ranks.Rank;
import me.kylesimmonds.serveressentials.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerFunctions {
    public ArrayList<SEPlayer> playerList = new ArrayList<>();

    /*
    Placeholders:

    {RankPrefix}
    {PlayerRank}
    {PlayerName}
    {PlayerBalance}
    {OnlinePlayers}
    {CurrencyName}

     */

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

    /***
     * Converts placeholders used in configs
     * @param p - Players
     * @param s - Message to convert
     * @return - Message with information inserted
     */
    public String convertPlaceholders(Player p, String s) {
        String[] placeholders = new String[]{"{RankPrefix}", "{PlayerRank}", "{PlayerName}", "{PlayerBalance}", "{OnlinePlayers}", "{CurrencyName}"};
        String text = s;

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        for (String string : placeholders) {
            if (s.contains(string)) {
                switch (string) {
                    default:
                        break;
                    case "{RankPrefix}":
                        text = text.replace(placeholders[0], ConfigManager.getInstance().getRanks().getString("Ranks." + ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank") + ".Prefix"));
                        break;
                    case "{PlayerRank}":
                        text = text.replace(placeholders[1], ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank"));
                        break;
                    case "{PlayerName}":
                        text = text.replace(placeholders[2], p.getName());
                        break;
                    case "{PlayerBalance}":
                        text = text.replace(placeholders[3], myFormat.format(ConfigManager.getInstance().getPlayers().getInt("Player." + p.getUniqueId().toString() + ".Balance")));
                        break;
                    case "{OnlinePlayers}":
                        int playerCount = Bukkit.getOnlinePlayers().size();
                        text = text.replace(placeholders[4], myFormat.format(playerCount));
                        break;
                    case "{CurrencyName}":
                        text = text.replace(placeholders[5], Main.getPlugin().getConfig().getString("currency-name"));
                        break;
                }
            }
        }
        return text;
    }


    /***
     * Shows users rank prefix under name
     * @param p - Player
     */
    public void displayRankBelowName(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("test", "test2");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getRanks().getString("Ranks." + ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank") + ".Prefix")));
        p.setScoreboard(board);
    }

    /***
     * Shows default scoreboard that is configured in config.yml
     */
    public void updateDefaultScoreboard() {
        if (!Main.getPlugin().getConfig().getBoolean("Scoreboard.default.scrolling-title")) {
            int line_limit = 15;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Main.getPlugin().getConfig().getBoolean("Scoreboard.default.enabled")) {
                    ScoreboardAPI api = new ScoreboardAPI(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Scoreboard.default.title")));

                    for (int i = 1; i < line_limit; i++) {
                        if (!(Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i) == null)) {
                            if ((Main.getPlugin().getConfig().getString("Scoreboard.default.line-") + i).isEmpty()) {
                                api.blankLine();
                            } else {
                                String s = Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i);
                                s = convertPlaceholders(p, s);
                                api.add(ChatColor.translateAlternateColorCodes('&', s));
                            }
                        }
                    }
                    api.build();
                    api.send(p);
                }
            }
        } else {
            showdefaultScrollingScoreboard();
        }
    }

    public void showdefaultScrollingScoreboard() {
        int line_limit = 15;
        for (Player p : Bukkit.getOnlinePlayers()) {
            new BukkitRunnable() {
                Scroll scroller = new Scroll(Main.getPlugin().getConfig().getString("Scoreboard.default.title"), Main.getPlugin().getConfig().getInt("Scoreboard.default.scrolling-border"), Main.getPlugin().getConfig().getInt("Scoreboard.default.scrolling-break"), '&');

                public void run() {
                    ScoreboardAPI api = new ScoreboardAPI(scroller.next());

                    for (int i = 1; i < line_limit; i++) {
                        if (!(Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i) == null)) {
                            if ((Main.getPlugin().getConfig().getString("Scoreboard.default.line-") + i).isEmpty()) {
                                api.blankLine();
                            } else {
                                String s = Main.getPlugin().getConfig().getString("Scoreboard.default.line-" + i);
                                s = convertPlaceholders(p, s);
                                api.add(ChatColor.translateAlternateColorCodes('&', s));
                            }
                        }
                    }
                    api.build();
                    api.send(p);
                }
            }.runTaskTimer(Main.getPlugin(), 0L, Main.getPlugin().getConfig().getInt("Scoreboard.default.scrolling-refresh-rate"));
        }
    }
}

