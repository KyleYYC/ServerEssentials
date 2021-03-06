package me.kylesimmonds.serveressentials.commands;

import me.kylesimmonds.serveressentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Spawns implements CommandExecutor {

    /*
    Command Structure:
      Command: /spawn
      Command: /setspawn

     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("spawn")) {
                Player p = (Player) sender;
                if (Main.getPlugin().getConfig().contains("Spawn.")) {
                    Location spawn = new Location(Bukkit.getWorld(Main.getPlugin().getConfig().getString("Spawn.World")),
                            Main.getPlugin().getConfig().getDouble("Spawn.X"),
                            Main.getPlugin().getConfig().getDouble("Spawn.Y"),
                            Main.getPlugin().getConfig().getDouble("Spawn.Z"),
                            Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Yaw")),
                            Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Pitch")));
                    p.teleport(spawn);
                    p.sendMessage(Main.prefix + ChatColor.GOLD + "Teleporting...");
                } else {
                    sender.sendMessage(Main.prefixWarn + ChatColor.RED + "Error. Please contact an administrator to fix this issue.");
                }
            }

            if (cmd.getName().equalsIgnoreCase("setspawn")) {
                Player p = (Player) sender;
                Main.getPlugin().getConfig().set("Spawn.Z", p.getLocation().getBlockZ());
                Main.getPlugin().getConfig().set("Spawn.X", p.getLocation().getBlockX());
                Main.getPlugin().getConfig().set("Spawn.Y", p.getLocation().getBlockY());
                Main.getPlugin().getConfig().set("Spawn.Z", p.getLocation().getBlockZ());
                Main.getPlugin().getConfig().set("Spawn.Yaw", p.getLocation().getYaw());
                Main.getPlugin().getConfig().set("Spawn.Pitch", p.getLocation().getPitch());
                Main.getPlugin().getConfig().set("Spawn.World", p.getLocation().getWorld().getName());
                Main.getPlugin().saveConfig();
                p.sendMessage(Main.prefix + ChatColor.GREEN + "Spawn set.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is only usable by players in game!");
        }
        return false;
    }
}

