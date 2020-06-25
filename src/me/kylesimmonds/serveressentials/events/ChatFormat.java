package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setFormat(ChatColor.translateAlternateColorCodes('&', getChatFormat(p, e.getMessage())));
    }

    /***
     * Gets the server chat format from config.yml
     * @param p - Player
     * @param message - Players message
     * @return - Chat Format
     */
    public String getChatFormat(Player p, String message) {
        String rankName = ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank");
        String rawFormat = Main.getPlugin().getConfig().getString("chat-format");
        rawFormat = rawFormat.replace("{RankPrefix}", ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getRanks().getString("Ranks." + rankName + ".Prefix")));
        rawFormat = rawFormat.replace("{PlayerName}", p.getDisplayName());
        rawFormat = rawFormat.replace("{Message}", message);
        return rawFormat;
    }
}
