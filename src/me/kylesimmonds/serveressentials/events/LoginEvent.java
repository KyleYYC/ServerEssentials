package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.economy.Wallet;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import me.kylesimmonds.serveressentials.players.SEPlayer;
import me.kylesimmonds.serveressentials.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        if (!e.getPlayer().hasPlayedBefore() && ConfigManager.getInstance().getPlayers().getConfigurationSection("Player." + e.getPlayer().getUniqueId().toString()) == null) {
            //Setup Player.yml profile
            Wallet wallet = new Wallet(e.getPlayer(), 0);
            Rank defaultRank = new Rank("Default", ConfigManager.getInstance().getRanks().getString("Ranks.Default.Prefix")); // Make sure it's the same rank as rankmanager
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".Name", e.getPlayer().getName());
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".JoinDate", formatter.format(date));
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".LastLogin", formatter.format(date));
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".Balance", wallet.getBalance());
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".LoginCount", 0);
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".Rank", defaultRank.getName()); //TODO NEED TO CREATE RANKNAME TO RANK METHOD
            ConfigManager.getInstance().savePlayers();


            SEPlayer sePlayer = new SEPlayer(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName(), defaultRank.rankNameToRank(defaultRank.getName()), 0, true);
            PlayerFunctions pf = new PlayerFunctions();
            pf.playerList.add(sePlayer);

            //Update statistics.yml
            int tp = ConfigManager.getInstance().getStatistics().getInt("total-players");
            ConfigManager.getInstance().getStatistics().set("total-players", tp + 1);
            ConfigManager.getInstance().saveStatistics();

            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.DARK_PURPLE + " has been added successfully to Server Essentials player list.");
        }
    }
}
