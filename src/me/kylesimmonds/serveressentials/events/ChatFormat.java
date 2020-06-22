package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

    public String getChatFormat(Player p, String message) {
        String rankName = ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank");
        String rawFormat = Main.getPlugin().getConfig().getString("chat-format");
        rawFormat = rawFormat.replace("{Prefix}", ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getRanks().getString("Ranks." + rankName + ".Prefix")));
        rawFormat = rawFormat.replace("{Name}", p.getName());
        rawFormat = rawFormat.replace("{Message}", message);
        return rawFormat;
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String rankName = ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank");
        e.setFormat(ChatColor.translateAlternateColorCodes('&', getChatFormat(p, e.getMessage())));
        }
}
