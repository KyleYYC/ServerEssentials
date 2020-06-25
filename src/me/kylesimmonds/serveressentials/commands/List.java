package me.kylesimmonds.serveressentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List implements CommandExecutor {
    //TODO: Make method to automatically format multiple pages

    /*
    Command Structure:
      Command: /list - (/playerlist)

     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            int playerCount = 0;

            sender.sendMessage(ChatColor.DARK_RED + "\n----------------------" + ChatColor.RED + "\nPlayer List" + ChatColor.DARK_RED + "\n----------------------");
            for (Player p : Bukkit.getOnlinePlayers()) {
                playerCount++;
                sender.sendMessage(ChatColor.RED + "" + playerCount + ")" + ChatColor.GOLD + "" + p.getName());
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Total Players Online: " + ChatColor.GOLD + playerCount);
            sender.sendMessage(ChatColor.DARK_RED + "\n----------------------");
        }
        return false;
    }
}
