package me.kylesimmonds.serveressentials.ranks;

import java.util.ArrayList;

public class Rank {

    public ArrayList<Rank> serverRanks = new ArrayList<>();

    private String name;
    private String prefix;

    /***
     * Rank constructor
     * @param name - Player Name
     * @param prefix - Rank Prefix
     */
    public Rank(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
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
