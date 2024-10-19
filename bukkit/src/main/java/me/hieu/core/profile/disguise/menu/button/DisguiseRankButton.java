package me.hieu.core.profile.disguise.menu.button;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.disguise.DisguiseProfile;
import me.hieu.core.rank.Rank;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.util.CC;
import me.hieu.libraries.util.ItemBuilder;
import me.hieu.libraries.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 17/10/2024
 */

public class DisguiseRankButton extends Button {

    private String name;
    private Rank rank;

    public DisguiseRankButton(String name, Rank rank){
        this.name = name;
        this.rank = rank;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add(CC.CHAT_BAR);
        lore.add("&9Click to disguise as &r" + rank.getFormattedRank() + " &9rank.");
        lore.add(CC.CHAT_BAR);
        return new ItemBuilder(Core.getInstance().getChatColorHandler().getMaterialMap().get(rank.getColor())).name("").lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        TaskUtil.runTask(player::closeInventory);
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        profile.setDisguiseProfile(new DisguiseProfile(name, rank.getUniqueId()));
        profile.save();
        profile.updateDisguise();
    }

}
