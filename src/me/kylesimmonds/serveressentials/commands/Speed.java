package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {

    /*
    Command Structure:
      Command: /speed
        /speed {Value}
        /speed Reset

     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("speed")) {
            if (sender instanceof Player) {
                if (args.length < 1 || args.length > 2) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/speed {WalkSpeed|Reset}");
                } else {
                    Player p = (Player) sender;
                    if (isInteger(args[0]) && Integer.parseInt(args[0]) <= 10) {
                        float i = Float.parseFloat(args[0]) / 10;
                        if (i < 1.000001) {
                            if (p.isFlying()) {
                                p.setFlySpeed(i);
                                sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set your flying speed to " + ChatColor.LIGHT_PURPLE + args[0]);
                            } else {
                                p.setWalkSpeed(i);
                                sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set your walking speed to " + ChatColor.LIGHT_PURPLE + args[0]);
                            }
                        }
                    } else {
                        if (!args[0].equalsIgnoreCase("reset")) {
                            sender.sendMessage(Main.prefix + ChatColor.RED + "Not a valid number or value is too high!" + ChatColor.DARK_RED + " (Server Limit is 10)");
                        }
                    }

                    if (args[0].equalsIgnoreCase("reset")) {
                        if (p.isFlying()) {
                            p.setFlySpeed((float) 0.2);
                            sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set your flying speed to " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "DEFAULT");
                        } else {
                            p.setWalkSpeed((float) 0.2);
                            sender.sendMessage(Main.prefix + ChatColor.GOLD + "You set your walking speed to " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "DEFAULT");
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }
}
