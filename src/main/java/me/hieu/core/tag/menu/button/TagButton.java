package me.hieu.core.tag.menu.button;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.tag.Tag;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.util.CC;
import me.hieu.libraries.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagButton extends Button {

    private Tag tag;

    public TagButton(Tag tag){
        this.tag = tag;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        List<String> lore = new ArrayList<>();
        lore.add(CC.MENU_BAR);
        if (player.hasPermission("tag." + tag.getName().toLowerCase())){
            if (profile.getTag() == null) {
                lore.add("&aClick to select &r" + tag.getFormattedName() + " &atag!");
            } else if (profile.getTag().getUniqueId() == tag.getUniqueId()){
                lore.add("&cClick to deselect &r" + tag.getFormattedName() + " &ctag!");
            }
        } else {
            lore.add("&cPurchase &r" + tag.getFormattedName() + " &ctag at www.hieu.com/store!");
        }
        lore.add(CC.MENU_BAR);
        return new ItemBuilder(Core.getInstance().getChatColorHandler().getMaterialMap().get(tag.getColor())).name(" ").lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        if (player.hasPermission("tag." + tag.getName().toLowerCase())){
            if (profile.getTag() == null) {
                profile.setTag(tag);
            } else if (profile.getTag().getUniqueId() == tag.getUniqueId()){
                profile.setTag(null);
            }
            playNeutral(player);
            profile.save();
            return;
        }
        playFail(player);
    }
}
