package me.kylesimmonds.serveressentials.ranks;

import java.util.ArrayList;

public class Rank {

    public ArrayList<Rank> serverRanks = new ArrayList<>();

    private String name;
    private String prefix;


    public Rank(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public Rank rankNameToRank(String rankname) {
        for (Rank rank : serverRanks) {
            if (rank.getName().equals(rankname)) {
                return rank;
            }
        }
        Rank rankError = new Rank("error", "error");
        return rankError;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
