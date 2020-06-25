package me.kylesimmonds.serveressentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;

public class MOTD {
    private String fileName = "motd.txt";
    private File motdFile = new File("plugins/ServerEssentials/" + fileName);

    //Handles server motd taking motd.txt

    /*
    {RankPrefix}
    {Rank}
    {PlayerName}
    {PlayerBalance}
     */

    public void loadMOTD() {
        //Check if motd file exists
        if (!motdFile.exists()) {
            try {
                FileWriter fw = new FileWriter("plugins/ServerEssentials/" + fileName);
                PrintWriter pw = new PrintWriter(fw);


                //Sets header
                pw.println("###################################################################");
                pw.println("#                        MOTD Configuartion                       #");
                pw.println("###################################################################");

                //Sets default motd
                pw.println("&6----------------------------------");
                pw.println("&dWelcome {RankPrefix} &c&l{PlayerName}&d!");
                pw.println("");
                pw.println("&d&lEnjoy your stay!");
                pw.println("&6----------------------------------");

                pw.close();
                Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "Created " + ChatColor.YELLOW + fileName + ChatColor.GREEN + " successfully.");
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.RED + "Critical error creating motd.txt");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "Loaded " + ChatColor.YELLOW + fileName + ChatColor.GREEN + " successfully.");
        }
    }

    public void sendMOTD(Player p) {
        try {
            FileReader fr = new FileReader("plugins/ServerEssentials/" + fileName);
            BufferedReader br = new BufferedReader(fr);

            String str;
            while ((str = br.readLine()) != null) {
                if (!str.matches("#.*")) {

                    String line = str.replace("{PlayerName}", p.getName());
                    line = line.replace("{RankPrefix}", ConfigManager.getInstance().getRanks().getString("Ranks." + ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank") + ".Prefix"));
                    line = line.replace("{Rank}", ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank"));
                    line = line.replace("{PlayerBalance}", ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Balance"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                }
            }
            br.close();

        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.RED + "Could not access " + ChatColor.YELLOW + fileName);
        }
    }
}
