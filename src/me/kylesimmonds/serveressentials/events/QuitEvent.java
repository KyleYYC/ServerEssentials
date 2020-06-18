package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', convertQuitPlaceholders(e.getPlayer())));
    }

    private String convertQuitPlaceholders(Player p) {
        String jm = Main.getPlugin().getConfig().getString("custom-quit-message");

        //List of placeholders
        String B = jm.replace("{PlayerName}", p.getName());
        return B;
    }
}
