package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class Balance implements CommandExecutor {
    /*

    /bal - gets users balance
    /bal {player} - gets an online players balance
    /bal {player} add {value}
    /bal {player} set {value}


     */
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bal")) {
            NumberFormat myFormat = NumberFormat.getInstance();
            myFormat.setGroupingUsed(true);

            if (!(sender instanceof ConsoleCommandSender)) {
                if (args.length == 0) {
                    Player p = (Player) sender;
                    sender.sendMessage(Main.prefix + ChatColor.GOLD + "Your balance is currently " + ChatColor.DARK_PURPLE + myFormat.format(ConfigManager.getInstance().getPlayers().getDouble("Player." + p.getUniqueId().toString() + ".Balance")) + " " + ChatColor.GOLD + Main.getPlugin().getConfig().getString("currency-name"));
                    return false;
                }

                if (args.length < 3 || args.length > 5) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/bal {player} SET|ADD {Value}");
                    return false;
                } else if (!(ConfigManager.getInstance().getPlayers().getConfigurationSection("Player." + Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId().toString()) == null)) { //Check if valid player
                    //Confirmed valid player

                    double value = Double.parseDouble(args[2]);
                    Player targetPlayer = (Player) Bukkit.getServer().getOfflinePlayer(args[0]);
                    if (args[1].equalsIgnoreCase("SET") && value > 0) {
                        //Cleared to set balance:
                        ConfigManager.getInstance().getPlayers().set("Player." + targetPlayer.getUniqueId() + ".Balance", value);
                        ConfigManager.getInstance().savePlayers();
                        sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set " + ChatColor.RED + targetPlayer.getName() + ChatColor.GOLD + " balance to " + ChatColor.DARK_RED + myFormat.format(value) + " " + ChatColor.GOLD + Main.getPlugin().getConfig().getString("currency-name"));
                        PlayerFunctions pf = new PlayerFunctions();
                        pf.refreshPlayerList();
                    }
                    if (args[1].equalsIgnoreCase("ADD") && value > 0) {
                        //Cleared to add balance:
                        double new_value = ConfigManager.getInstance().getPlayers().getDouble("Player." + targetPlayer.getUniqueId() + ".Balance") + value;
                        ConfigManager.getInstance().getPlayers().set("Player." + targetPlayer.getUniqueId() + ".Balance", new_value);
                        ConfigManager.getInstance().savePlayers();
                        sender.sendMessage(Main.prefix + ChatColor.GOLD + "You added " + ChatColor.RED + myFormat.format(value) + " " + ChatColor.GOLD + Main.getPlugin().getConfig().getString("currency-name") + ChatColor.GOLD + " to " + ChatColor.DARK_PURPLE + targetPlayer.getName() + "'s " + ChatColor.GOLD + "balance!");
                        sender.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + targetPlayer.getName() + ChatColor.GOLD + " balance is now: " + ChatColor.RED + myFormat.format(new_value) + " " + ChatColor.GOLD + Main.getPlugin().getConfig().getString("currency-name"));
                        PlayerFunctions pf = new PlayerFunctions();
                        pf.refreshPlayerList();
                    }
                } else {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "There is no valid player by the name of " + ChatColor.DARK_PURPLE + args[0]);
                    return false;
                }
            } else {
                sender.sendMessage(Main.prefix + ChatColor.RED + "This is currently only an in game command.");
                return false;
            }


        }
        return false;
    }
}
