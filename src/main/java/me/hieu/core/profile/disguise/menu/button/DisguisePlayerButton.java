package me.hieu.core.profile.disguise.menu.button;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.disguise.DisguiseProfile;
import me.hieu.core.profile.disguise.menu.DisguiseRankMenu;
import me.hieu.libraries.menu.Button;
import me.hieu.libraries.util.CC;
import me.hieu.libraries.util.SkullBuilder;
import me.hieu.libraries.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 17/10/2024
 */

public class DisguisePlayerButton extends Button {

    private String name;

    public DisguisePlayerButton(String name){
        this.name = name;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add(CC.CHAT_BAR);
        lore.add("&9Click to disguise as &a" + name + "&9.");
        lore.add(CC.CHAT_BAR);
        return new SkullBuilder(name).name("").lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        new DisguiseRankMenu(name).openMenu(player);
    }

}
