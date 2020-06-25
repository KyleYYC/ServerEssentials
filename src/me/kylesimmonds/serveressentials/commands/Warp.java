package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor {

    /*
    Command Structure:
      Command: /warp - (/warps)
        /warp set {warpName}

     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length > 3) {
                sender.sendMessage(Main.prefix + ChatColor.RED + "Improper usage: " + ChatColor.GOLD + "/warp {name}");
                return false;
            } else {
                if (args.length == 0) {
                    listWarps(sender);
                    return false;
                }
                if (args[0].equalsIgnoreCase("set") && args.length == 2 && sender instanceof Player) {
                    //Add warp
                    Player p = (Player) sender;
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".Z", p.getLocation().getBlockZ());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".X", p.getLocation().getBlockX());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".Y", p.getLocation().getBlockY());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".Z", p.getLocation().getBlockZ());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".Yaw", p.getLocation().getYaw());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".Pitch", p.getLocation().getPitch());
                    Main.getPlugin().getConfig().set("Warps." + args[1] + ".World", p.getLocation().getWorld().getName());
                    Main.getPlugin().saveConfig();
                    sender.sendMessage(Main.prefix + ChatColor.GREEN + "You have successfully made " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.GREEN + " a warp on the server!");
                    return false;
                }

                if (isWarpValid(args[0]) && sender instanceof Player) {
                    Player p = (Player) sender;
                    Location warp = new Location(Bukkit.getWorld(Main.getPlugin().getConfig().getString("Warps." + getProperWarpName(args[0]) + ".World")),
                            Main.getPlugin().getConfig().getDouble("Warps." + getProperWarpName(args[0]) + ".X"),
                            Main.getPlugin().getConfig().getDouble("Warps." + getProperWarpName(args[0]) + ".Y"),
                            Main.getPlugin().getConfig().getDouble("Warps." + getProperWarpName(args[0]) + ".Z"),
                            Float.parseFloat(Main.getPlugin().getConfig().getString("Warps." + getProperWarpName(args[0]) + ".Yaw")),
                            Float.parseFloat(Main.getPlugin().getConfig().getString("Warps." + getProperWarpName(args[0]) + ".Pitch")));
                    p.teleport(warp);
                    p.sendMessage(Main.prefix + ChatColor.GOLD + "Teleporting to" + ChatColor.LIGHT_PURPLE + " " + getProperWarpName(args[0]) + ChatColor.GOLD + "...");
                }

            }
        }
        return false;
    }

    private void listWarps(CommandSender sender) {
        if (Main.getPlugin().getConfig().getConfigurationSection("Warps") == null) {
            sender.sendMessage(Main.prefix + ChatColor.RED + "No available warps to list at this time.");
        } else {
            int i = 0;
            for (String warps : Main.getPlugin().getConfig().getConfigurationSection("Warps").getKeys(false)) {
                i++;
                sender.sendMessage(Main.prefix + ChatColor.GOLD + i + ")" + ChatColor.AQUA + " " + warps);
            }
            sender.sendMessage(Main.prefix + ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "---------------------------------");
        }

    }

    public boolean isWarpValid(String warp) {
        if (Main.getPlugin().getConfig().getConfigurationSection("Warps") == null) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.RED + "Attempted to verify warp " + ChatColor.GOLD + warp + ChatColor.RED + " and failed.");
            return false;
        }

        for (String warps : Main.getPlugin().getConfig().getConfigurationSection("Warps").getKeys(false)) {
            if (warps.equalsIgnoreCase(warp)) {
                return true;
            }
        }
        return false;
    }

    private String getProperWarpName(String s) {
        String warpName = s;
        for (String warps : Main.getPlugin().getConfig().getConfigurationSection("Warps").getKeys(false)) {
            if (warps.equalsIgnoreCase(s)) {
                warpName = warps;
            }
        }
        return warpName;
    }
}
