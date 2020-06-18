package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.MOTD;
import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinEvent implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', convertJoinPlaceholders(e.getPlayer()))); //Set's join message
        ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".LastLogin", formatter.format(date)); //Updates last login
        ConfigManager.getInstance().savePlayers();

        if (Main.getPlugin().getConfig().contains("Spawn.") && Main.getPlugin().getConfig().getConfigurationSection("Spawn") != null) {
            Location spawn = new Location(Bukkit.getWorld(Main.getPlugin().getConfig().getString("Spawn.World")),
                    Main.getPlugin().getConfig().getDouble("Spawn.X"),
                    Main.getPlugin().getConfig().getDouble("Spawn.Y"),
                    Main.getPlugin().getConfig().getDouble("Spawn.Z"),
                    Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Yaw")),
                    Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Pitch")));
            e.getPlayer().teleport(spawn);
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "No spawn has been set.");
        }

        if (Main.getPlugin().getConfig().getBoolean("join-sound")) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 5, 55);
        }
        if (Main.getPlugin().getConfig().getBoolean("motd-enabled")) {
            MOTD.sendMOTD(e.getPlayer());
        }
    }

    private String convertJoinPlaceholders(Player p) {
        //P
        String jm = Main.getPlugin().getConfig().getString("custom-join-message");
        //List of placeholders
        String B = jm.replace("{PlayerName}", p.getName());
        return B;
    }
}
