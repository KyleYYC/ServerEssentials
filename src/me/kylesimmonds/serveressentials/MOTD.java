package me.kylesimmonds.serveressentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;

public class MOTD {
    private static String fileName = "motd.txt";
    private static File motdFile = new File("plugins/ServerEssentials/" + fileName);

    //Handles server motd taking motd.txt


    public static void loadMOTD() {
        //Check if motd file exists
        if (!motdFile.exists()) {
            try {
                FileWriter fw = new FileWriter("plugins/ServerEssentials/" + fileName);
                PrintWriter pw = new PrintWriter(fw);

                //Sets default motd
                pw.println("&6----------------------------");
                pw.println("&dWelcome to the server!");
                pw.println("");
                pw.println("&d&lEnjoy your stay!");
                pw.println("&6----------------------------");

                pw.close();
                Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "Created " + ChatColor.YELLOW + fileName + ChatColor.GREEN + " successfully.");
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.RED + "Critical error creating motd.txt");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + ChatColor.GREEN + "Loaded " + ChatColor.YELLOW + fileName + ChatColor.GREEN + " successfully.");
        }
    }

    public static void sendMOTD(Player p) {
        //TODO In future add place holders such as {PlayerName}
        try {
            FileReader fr = new FileReader("plugins/ServerEssentials/" + fileName);
            BufferedReader br = new BufferedReader(fr);

            String str;
            while ((str = br.readLine()) != null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
            }
            br.close();

        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Main.prefixWarn + ChatColor.RED + "Could not access " + ChatColor.YELLOW + fileName);
        }
    }
}
