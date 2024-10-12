package me.hieu.core.grant.menu.button;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.ProfileHandler;
import me.hieu.core.rank.Rank;
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
 * Date: 07/10/2024
 */

@Getter
public class GrantButton extends Button {

    private final Profile target;
    private final Grant grant;

    public GrantButton(Profile target, Grant grant){
        this.target = target;
        this.grant = grant;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        Core plugin = Core.getInstance();
        ProfileHandler profileHandler = plugin.getProfileHandler();
        RankHandler rankHandler = plugin.getRankHandler();
        ItemStack item = null;
        List<String> lore = new ArrayList<>();
        lore.add(CC.MENU_BAR);
        lore.add("&eAdded Rank: &r" + grant.getGrantRank().getFormattedRank());
        if (grant.getAddedBy().equals(ConsoleUtil.CONSOLE_UUID)){
            lore.add("&eAdded By: &4Console");
        } else {
            lore.add("&eAdded By: &r" + profileHandler.getProfileByUniqueId(grant.getAddedBy()).getFormattedName());
        }
        lore.add("&eAdded Duration: &c" + grant.getGrantDuration());
        lore.add("&eAdded Reason: &c" + grant.getAddedReason());
        if (!grant.isExpired() && !grant.isPardoned()){
            item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            lore.add("&eExpires In: &c" + grant.getExpiresIn());
        }
        lore.add(CC.MENU_BAR);
        if (!grant.isExpired() && !grant.isPardoned()){
            Rank defaultRank = rankHandler.getRankByName("Default");
            if (grant.getGrantRank().getName().equalsIgnoreCase(defaultRank.getName())){
                lore.add("&cYou can't pardon this grant!");
            } else {
                lore.add("&aClick to pardon this grant!");
            }
        } else if (grant.isExpired() && !grant.isPardoned()) {
            item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            lore.add("&6&lGrant Expired:");
            lore.add("&eExpired On: &c" + DateUtil.format(grant.getExpireDate()));
        } else if (grant.isPardoned()) {
            Profile profile = profileHandler.getProfileByUniqueId(grant.getPardonedBy());
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            lore.add("&c&lGrant Pardoned:");
            lore.add("&ePardoned By: &r" + (profile == null ? "&4Console" : profile.getFormattedName()));
            lore.add("&ePardoned On: &c" + DateUtil.format(grant.getPardonedOn()));
            lore.add("&ePardoned Reason: &c" + grant.getPardonedReason());
        }
        lore.add(CC.MENU_BAR);
        String name = "&6" + DateUtil.format(grant.getAddedOn());
        return new ItemBuilder(item).name(name).lore(lore).build();
    }

}
