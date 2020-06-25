package me.kylesimmonds.serveressentials.players;

import me.kylesimmonds.serveressentials.ranks.Rank;

public class SEPlayer {
    private String uuid;
    private String playerName;
    private Rank rank;
    private int playerBalance;


    public SEPlayer(String uuidString, String name, Rank rank, int balance) {
        uuid = uuidString;
        playerName = name;
        this.rank = rank;
        playerBalance = balance;

    }


    public Rank getRankName() {
        return this.rank;
    }

    public void setRankName(Rank rank) {
        this.rank = rank;
    }


    public int getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(int playerBalance) {
        this.playerBalance = playerBalance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
