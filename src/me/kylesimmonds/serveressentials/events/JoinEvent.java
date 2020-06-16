package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.MOTD;
import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
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
}
