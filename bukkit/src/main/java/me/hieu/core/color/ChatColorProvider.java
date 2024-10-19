package me.hieu.core.color;

import me.hieu.libraries.drink.argument.CommandArg;
import me.hieu.libraries.drink.exception.CommandExitMessage;
import me.hieu.libraries.drink.parametric.DrinkProvider;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 02/10/2024
 */

public class ChatColorProvider extends DrinkProvider<ChatColor> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Nullable
    @Override
    public ChatColor provide(@NotNull CommandArg commandArg, @NotNull List<? extends Annotation> list) throws CommandExitMessage {
        String name = commandArg.get();
        ChatColor color = ChatColor.valueOf(name);
        if (color.isColor()) return color;
        throw new CommandExitMessage("No chat color with the name '" + name + "'.");
    }

    @Override
    public String argumentDescription() {
        return "color";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (ChatColor color : ChatColor.values()){
            if (color.name().toLowerCase().startsWith(prefix.toLowerCase())) toReturn.add(color.name());
        }
        return toReturn;
    }

}
