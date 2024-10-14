package me.hieu.core.tag.menu;

import me.hieu.core.Core;
import me.hieu.core.tag.Tag;
import me.hieu.core.tag.menu.button.TagButton;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagsMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&6Tags &7(" + Core.getInstance().getTagHandler().getTags().size() + ")";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonMap = new HashMap<>();
        for (Tag tag : Core.getInstance().getTagHandler().getTags()){
            buttonMap.put(buttonMap.size(), new TagButton(tag));
        }
        return buttonMap;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9;
    }

}
