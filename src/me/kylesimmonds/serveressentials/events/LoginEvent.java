package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.economy.Wallet;
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
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".Name", e.getPlayer().getName());
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".JoinDate", formatter.format(date));
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".LastLogin", formatter.format(date));
            ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".Balance", wallet.getBalance());
            ConfigManager.getInstance().savePlayers();

            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.DARK_PURPLE + " has been added to players.yml");
        }
    }
}