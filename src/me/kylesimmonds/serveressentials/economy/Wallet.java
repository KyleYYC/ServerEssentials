package me.kylesimmonds.serveressentials.economy;

import org.bukkit.entity.Player;

public class Wallet {
    private Player player;
    private int bal;

    public Wallet(Player p) {
        player = p;
    }

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

    public int getBalance() {
        return bal;
    }

    public void setBalance(int bal) {
        this.bal = bal;
    }
}
