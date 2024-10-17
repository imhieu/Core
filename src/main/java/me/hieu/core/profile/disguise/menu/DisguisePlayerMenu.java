package me.hieu.core.profile.disguise.menu;

import me.hieu.core.Core;
import me.hieu.core.profile.disguise.menu.button.DisguisePlayerButton;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Le Thanh Hieu
 * Date: 17/10/2024
 */

public class DisguisePlayerMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&6Disguise &7(" + Core.getInstance().getConfigHandler().getDisguiseConfigz().getStringList("names").size() + ")";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonMap = new HashMap<>();
        List<String> names = Core.getInstance().getConfigHandler().getDisguiseConfigz().getStringList("names");
        for (String name : names){
            buttonMap.put(buttonMap.size(), new DisguisePlayerButton(name));
        }
        return buttonMap;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9;
    }
}
