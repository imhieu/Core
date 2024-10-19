package me.hieu.core.grant.menu;

import lombok.Getter;
import me.hieu.core.grant.Grant;
import me.hieu.core.grant.menu.button.GrantButton;
import me.hieu.core.profile.Profile;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Le Thanh Hieu
 * Date: 07/10/2024
 */

@Getter
public class GrantsMenu extends PaginatedMenu {

    private final Profile target;

    public GrantsMenu(Profile target){
        this.target = target;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return target.getFormattedName() + " &7(" + target.getGrants().size() + ")";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonMap = new HashMap<>();
        List<Grant> grants = target.getGrants();
        Collections.sort(grants);
        Collections.reverse(grants);
        for (Grant grant : grants){
            buttonMap.put(buttonMap.size(), new GrantButton(target, grant));
        }
        return buttonMap;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9;
    }

}
