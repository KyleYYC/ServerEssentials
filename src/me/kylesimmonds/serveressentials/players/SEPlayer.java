package me.kylesimmonds.serveressentials.players;

public class SEPlayer {
    private String uuid;
    private String playerName;
    private String rankName;
    private int playerBalance;
    private boolean isVerified;

    public SEPlayer(String uuidString, String name, String rank, int balance, boolean verified) {
        uuid = uuidString;
        playerName = name;
        rankName = rank;
        playerBalance = balance;
        isVerified = verified;

    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
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
