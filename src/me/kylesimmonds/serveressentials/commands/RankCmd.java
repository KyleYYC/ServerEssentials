package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import me.kylesimmonds.serveressentials.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCmd implements CommandExecutor {

    /*
    Command Structure:
      Command: /rank - (/ranks)
        /rank add {rankName} {rankPrefix}
        /rank set {playerName} {rankName}

     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (args.length < 2 || args.length > 5) {
                sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/rank ADD|SET");
                return false;

            } else if (args[0].equalsIgnoreCase("ADD")) {
                if (args.length < 3 || args.length > 4) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/rank ADD {rankName} {prefix}");
                    return false;
                } else {
                    //Success
                    Rank rank = new Rank(args[1], args[2]);
                    rank.serverRanks.add(rank);

                    ConfigManager.getInstance().getRanks().set("Ranks." + args[1] + ".Prefix", args[2]);
                    ConfigManager.getInstance().saveRanks();
                    sender.sendMessage(Main.prefix + ChatColor.AQUA + "Successfully added " + ChatColor.YELLOW + args[1] + ChatColor.AQUA + " to ranks.yml");
                    return false;
                }

            } else if (args[0].equalsIgnoreCase("SET")) {
                if (args.length < 3 || args.length > 4) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/rank SET {Player} {Rank}");
                    return false;
                } else {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayers.getName().equalsIgnoreCase(args[1])) {
                            Player pl = Bukkit.getPlayer(args[1]);

                            for (String playersUUIDs : ConfigManager.getInstance().getPlayers().getConfigurationSection("Player").getKeys(false)) {
                                if (playersUUIDs.equals(pl.getUniqueId().toString())) {
                                    for (String rankNames : ConfigManager.getInstance().getRanks().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (args[2].equalsIgnoreCase(rankNames)) {
                                            ConfigManager.getInstance().getPlayers().set("Player." + pl.getUniqueId().toString() + ".Rank", rankNames);
                                            ConfigManager.getInstance().savePlayers();
                                            sender.sendMessage(Main.prefix + ChatColor.AQUA + "You set " + ChatColor.LIGHT_PURPLE + pl.getName() + ChatColor.AQUA + " rank to " + ChatColor.LIGHT_PURPLE + args[2]);
                                            PlayerFunctions pf = new PlayerFunctions();
                                            pf.refreshPlayerList();
                                            pf.updateDefaultScoreboard();
                                            return false;
                                        }
                                    }
                                    sender.sendMessage(Main.prefix + ChatColor.RED + "This rank does not exist!");
                                }
                            }
                        } else {
                            sender.sendMessage(Main.prefix + ChatColor.RED + "The player " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.RED + " does not exist or is not online.");
                        }
                    }
                }
            }
        }
        return false;
    }
}
