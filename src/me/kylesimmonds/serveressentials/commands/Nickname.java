package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nickname implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nick")) {
            if (args.length == 1) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(onlinePlayers.getName())) {
                        Player p = onlinePlayers;
                        if (ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Nickname") == null) {
                            sender.sendMessage(Main.prefix + ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GOLD + " has no nickname set.");
                            return false;
                        } else {
                            sender.sendMessage(Main.prefix + ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GOLD + " nickname is " + ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Nickname")));
                            return false;
                        }
                    }
                }
            } else if (args.length == 2) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(onlinePlayers.getName())) {
                        Player p = onlinePlayers;
                        if (args[1].equalsIgnoreCase("clear")) {
                            p.setPlayerListName(null);
                            p.setDisplayName(null);
                            ConfigManager.getInstance().getPlayers().set("Player." + p.getUniqueId().toString() + ".Nickname", null);
                            ConfigManager.getInstance().savePlayers();
                            sender.sendMessage(Main.prefix + ChatColor.GOLD + "You cleared " + ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GOLD + " nickname.");
                            return false;
                        } else {
                            ConfigManager.getInstance().getPlayers().set("Player." + p.getUniqueId().toString() + ".Nickname", args[1]);
                            ConfigManager.getInstance().savePlayers();
                            p.setDisplayName(ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Nickname"));
                            p.setPlayerListName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Nickname")));
                            sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set " + ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GOLD + " nickname to " + ChatColor.translateAlternateColorCodes('&', args[1]));
                            return false;
                        }
                    }
                }

            } else if (args.length > 3 || args.length == 0) {
                sender.sendMessage(Main.prefix + ChatColor.RED + "Improper Usage. " + ChatColor.GOLD + "/nick " + ChatColor.LIGHT_PURPLE + "{Player} {Nickname|Clear}");
                return false;
            }
        }
        return false;
    }
}
