package me.hieu.core.profile.disguise.menu;

import me.hieu.core.Core;
import me.hieu.core.profile.disguise.menu.button.DisguiseRankButton;
import me.hieu.core.rank.Rank;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Le Thanh Hieu
 * Date: 17/10/2024
 */

public class DisguiseRankMenu extends PaginatedMenu {

    private String name;

    public DisguiseRankMenu(String name){
        this.name = name;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&6Disguise &7(" + Core.getInstance().getRankHandler().getRanks().size() + ")";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonMap = new HashMap<>();
        List<Rank> ranks = Core.getInstance().getRankHandler().getRanks();
        Collections.sort(ranks);
        for (Rank rank : ranks){
            buttonMap.put(buttonMap.size(), new DisguiseRankButton(name, rank));
        }
        return buttonMap;
    }

}
