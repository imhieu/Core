package me.hieu.core.tag;

import me.hieu.core.Core;
import me.hieu.libraries.drink.argument.CommandArg;
import me.hieu.libraries.drink.exception.CommandExitMessage;
import me.hieu.libraries.drink.parametric.DrinkProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

public class TagProvider extends DrinkProvider<Tag> {


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
    public Tag provide(@NotNull CommandArg commandArg, @NotNull List<? extends Annotation> list) throws CommandExitMessage {
        String name = commandArg.get().replaceAll("_", " ");
        Tag tag = Core.getInstance().getTagHandler().getTagByName(name);
        if (tag != null) return tag;
        throw new CommandExitMessage("No tag with the name '" + name + "'.");
    }

    @Override
    public String argumentDescription() {
        return "tag";
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (Tag tag : Core.getInstance().getTagHandler().getTags()){
            if (tag.getName().replaceAll(" ", "_").toLowerCase().startsWith(prefix.replaceAll(" ", "_").toLowerCase())) toReturn.add(tag.getName().replaceAll(" ", "_"));
        }
        return toReturn;
    }

}
