package me.hieu.core.tag.command;

import me.hieu.core.tag.menu.TagsMenu;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagsCommand {

    @Command(name = "", desc = "view all tags")
    @Require("")
    public void command(@Sender Player player){
        new TagsMenu().openMenu(player);
    }

}
