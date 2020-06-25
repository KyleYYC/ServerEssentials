package me.kylesimmonds.serveressentials.economy;

import org.bukkit.entity.Player;

public class Wallet {
    private Player player;
    private double bal;

    /***
     * Creates a player wallet which is used to hold currency
     * @param p - Player
     * @param balance - Player Balance
     */
    public Wallet(Player p, int balance) {
        player = p;
        bal = balance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getBalance() {
        return bal;
    }

    public void setBalance(double bal) {
        this.bal = bal;
    }
}
