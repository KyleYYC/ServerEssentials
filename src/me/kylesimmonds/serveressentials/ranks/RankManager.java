package me.kylesimmonds.serveressentials.ranks;

import me.kylesimmonds.serveressentials.ConfigManager;

public class RankManager {

    /***
     * Gets a users server rank
     * @param uuid - Players UUID
     * @return - Users rank
     */
    public Rank getPlayerRank(String uuid) {
        String rankName = ConfigManager.getInstance().getPlayers().getString("Player." + uuid + ".Rank");
        Rank rank = new Rank("Error", "Error");

        for (String rankNames : ConfigManager.getInstance().getRanks().getStringList("Rank")) {
            if (rankName.equals(rankNames)) {
                //Verified rank
                String rankPrefix = ConfigManager.getInstance().getRanks().getString("Rank." + rankName + ".Prefix");
                rank = new Rank(rankName, rankPrefix);
            }
        }
        return rank;
    }

    public void applyRankDefault() {
        ConfigManager.getInstance().getRanks().set("Ranks.Default.Prefix", "&8Default");
        ConfigManager.getInstance().saveRanks();
    }
}
