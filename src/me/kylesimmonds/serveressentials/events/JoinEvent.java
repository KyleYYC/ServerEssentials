package me.kylesimmonds.serveressentials.events;

import me.kylesimmonds.serveressentials.ConfigManager;
import me.kylesimmonds.serveressentials.MOTD;
import me.kylesimmonds.serveressentials.Main;
import me.kylesimmonds.serveressentials.players.PlayerFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinEvent implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        //Updates Last login & Join message
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', convertJoinPlaceholders(e.getPlayer())));
        ConfigManager.getInstance().getPlayers().set("Player." + e.getPlayer().getUniqueId().toString() + ".LastLogin", formatter.format(date));
        ConfigManager.getInstance().savePlayers();
        //------

        //Send user to spawn if one is set.
        if (Main.getPlugin().getConfig().contains("Spawn.") && Main.getPlugin().getConfig().getConfigurationSection("Spawn") != null) {
            Location spawn = new Location(Bukkit.getWorld(Main.getPlugin().getConfig().getString("Spawn.World")),
                    Main.getPlugin().getConfig().getDouble("Spawn.X"),
                    Main.getPlugin().getConfig().getDouble("Spawn.Y"),
                    Main.getPlugin().getConfig().getDouble("Spawn.Z"),
                    Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Yaw")),
                    Float.parseFloat(Main.getPlugin().getConfig().getString("Spawn.Pitch")));
            e.getPlayer().teleport(spawn);
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.YELLOW + "No spawn has been set.");
        }
        //-------

        //Join Effects:
        if (Main.getPlugin().getConfig().getBoolean("join-sound")) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 5, 55);
        }
        //--------

        //MOTD
        if (Main.getPlugin().getConfig().getBoolean("motd-enabled")) {
            MOTD motd = new MOTD();
            motd.sendMOTD(e.getPlayer());
        }
        //------

        //Refresh player list:
        PlayerFunctions pf = new PlayerFunctions();
        pf.refreshPlayerList();

        //Display rank under name
        for (Player online : Bukkit.getOnlinePlayers()) {
            pf.displayRankBelowName(online);
        }

        //Show scoreboard sidebar
        pf.showSidebarScoreboard(e.getPlayer());

    }

    private String convertJoinPlaceholders(Player p) {
        //P
        String jm = Main.getPlugin().getConfig().getString("custom-join-message");
        //List of placeholders
        String B = jm.replace("{PlayerName}", p.getName());
        B = B.replace("{Rank}", ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank"));
        B = B.replace("{RankPrefix}", ConfigManager.getInstance().getRanks().getString("Ranks." + ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank") + ".Prefix"));
        return B;
    }
}
