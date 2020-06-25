package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        String qm = Main.getPlugin().getConfig().getString("custom-quit-message");

        PlayerFunctions pf = new PlayerFunctions();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', pf.convertPlaceholders(e.getPlayer(), qm)));
    }
}
