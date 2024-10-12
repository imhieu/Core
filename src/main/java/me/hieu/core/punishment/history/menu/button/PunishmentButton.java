package me.hieu.core.punishment.history.menu.button;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.ProfileHandler;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.rank.RankHandler;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.core.util.DateUtil;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.util.CC;
import me.hieu.libraries.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 11/10/2024
 */

@Getter
public class PunishmentButton extends Button {

    private Profile target;
    private Punishment punishment;

    public PunishmentButton(Profile target, Punishment punishment){
        this.target = target;
        this.punishment = punishment;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        Core plugin = Core.getInstance();
        ProfileHandler profileHandler = plugin.getProfileHandler();
        ItemStack item = null;
        List<String> lore = new ArrayList<>();
        lore.add(CC.MENU_BAR);
        lore.add("&ePunishment: &r" + punishment.getType().getFormattedContext());
        if (punishment.getAddedBy().equals(ConsoleUtil.CONSOLE_UUID)){
            lore.add("&eAdded By: &4Console");
        } else {
            lore.add("&eAdded By: &r" + profileHandler.getProfileByUniqueId(punishment.getAddedBy()).getFormattedName());
        }
        lore.add("&eAdded Duration: &c" + punishment.getPunishmentDuration());
        lore.add("&eAdded Reason: &c" + punishment.getAddedReason());
        if (!punishment.isExpired() && !punishment.isPardoned()){
            item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            lore.add("&eExpires In: &c" + punishment.getExpiresIn());
        }
        lore.add(CC.MENU_BAR);
        if (!punishment.isExpired() && !punishment.isPardoned()){
            lore.add("&aClick to pardon this history!");
        } else if (punishment.isExpired() && !punishment.isPardoned()) {
            item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            lore.add("&6&lPunishment Expired:");
            lore.add("&eExpired On: &c" + DateUtil.format(punishment.getExpireDate()));
        } else if (punishment.isPardoned()) {
            Profile profile = profileHandler.getProfileByUniqueId(punishment.getPardonedBy());
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            lore.add("&c&lPunishment Pardoned:");
            lore.add("&ePardoned By: &r" + (profile == null ? "&4Console" : profile.getFormattedName()));
            lore.add("&ePardoned On: &c" + DateUtil.format(punishment.getPardonedOn()));
            lore.add("&ePardoned Reason: &c" + punishment.getPardonedReason());
        }
        lore.add(CC.MENU_BAR);
        String name = "&6" + DateUtil.format(punishment.getAddedOn());
        return new ItemBuilder(item).name(name).lore(lore).build();
    }

}
