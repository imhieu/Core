package me.hieu.core.punishment.history.menu;

import lombok.Getter;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.history.menu.button.PunishmentButton;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Le Thanh Hieu
 * Date: 11/10/2024
 */

@Getter
public class HistoryMenu extends PaginatedMenu {

    private Profile target;

    public HistoryMenu(Profile target){
        this.target = target;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return target.getFormattedName() + " &7(" + target.getPunishments().size() + ")";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonMap = new HashMap<>();
        List<Punishment> punishments = target.getPunishments();
        Collections.sort(punishments);
        Collections.reverse(punishments);
        for (Punishment punishment : punishments){
            buttonMap.put(buttonMap.size(), new PunishmentButton(target, punishment));
        }
        return buttonMap;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9;
    }

}
