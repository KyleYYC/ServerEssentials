package me.kylesimmonds.serveressentials.players;

import me.kylesimmonds.serveressentials.ranks.Rank;

public class SEPlayer {
    private String uuid;
    private String playerName;
    private Rank rank;
    private int playerBalance;
    private boolean isVerified;

    public SEPlayer(String uuidString, String name, int balance, boolean verified) {
        uuid = uuidString;
        playerName = name;
        playerBalance = balance;
        isVerified = verified;

    }

    public SEPlayer(String uuidString, String name, Rank rank, int balance, boolean verified) {
        uuid = uuidString;
        playerName = name;
        this.rank = rank;
        playerBalance = balance;
        isVerified = verified;

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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
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
