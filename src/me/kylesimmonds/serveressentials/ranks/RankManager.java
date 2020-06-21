package me.kylesimmonds.serveressentials.ranks;

import me.kylesimmonds.serveressentials.ConfigManager;
import org.bukkit.entity.Player;

public class RankManager {

    public Rank getPlayerRank(Player p) {
        String rankName = ConfigManager.getInstance().getPlayers().getString("Player." + p.getUniqueId().toString() + ".Rank");
        for (String rankNames : ConfigManager.getInstance().getRanks().getStringList("Rank")) {
            if (rankName.equals(rankNames)) {
                //Verified rank
                String rankPrefix = ConfigManager.getInstance().getRanks().getString("Rank." + rankName + ".Prefix");
                Rank rank = new Rank(rankName, rankPrefix);
                return rank;
            }
        }
        Rank rankError = new Rank("error", "error");
        return rankError;
    }

    public void applyRankDefault() {
        ConfigManager.getInstance().getRanks().set("Ranks.Default.Prefix", "&8Default");
        ConfigManager.getInstance().saveRanks();
    }
}
