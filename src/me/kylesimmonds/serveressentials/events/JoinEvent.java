package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {
    Location spawn = new Location(Bukkit.getWorld(Main.getPlugin().getConfig().getString("Spawn.World")),
            Main.getPlugin().getConfig().getDouble("Spawn.X"),
            Main.getPlugin().getConfig().getDouble("Spawn.Y"),
            Main.getPlugin().getConfig().getDouble("Spawn.Z"),
            Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Yaw")),
            Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Pitch")));
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Main.getPlugin().getConfig().contains("Spawn.")) {
            e.getPlayer().teleport(spawn);
        } else {
            e.getPlayer().sendMessage(ChatColor.RED + "Looks like the server administrator forgot to setup the /spawn!");
        }

        if (Main.getPlugin().getConfig().getBoolean("join-sound")) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 5,55);
        }

        if (!Main.getPlugin().getConfig().getBoolean("advanced-motd")) {
            e.getPlayer().sendMessage(Main.prefix + ChatColor.GOLD + Main.getPlugin().getConfig().get("simple-motd"));
        } else {
            //TODO plan advanced motd
        }
    }
}
