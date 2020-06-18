package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.economy.Wallet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {
    /*

    /bal - gets users balance
    /bal {player} - gets an online players balance
    /bal {player} add {value}
    /bal {player} set {value}


     */
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bal")) {
            if (!(sender instanceof ConsoleCommandSender)) {
                Player p = (Player) sender;
                Wallet wallet = new Wallet(p, ConfigManager.getInstance().getPlayers().getInt("Player." + p.getUniqueId().toString() + ".Balance"));
                p.sendMessage(Main.prefix + ChatColor.GOLD + "Your balance is: " + ChatColor.RED + wallet.getBalance());
            } else {
                sender.sendMessage(Main.prefixWarn + ChatColor.RED + "This command is only usable by players. Use /bal {Player} SET|ADD {Value}");
            }
        }
        return false;
    }
}
