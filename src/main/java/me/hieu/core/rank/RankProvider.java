package me.hieu.core.rank;

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

public class RankProvider extends DrinkProvider<Rank> {


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
    public Rank provide(@NotNull CommandArg commandArg, @NotNull List<? extends Annotation> list) throws CommandExitMessage {
        String name = commandArg.get().replaceAll("_", " ");
        Rank rank = Core.getInstance().getRankHandler().getRankByName(name);
        if (rank != null) return rank;
        throw new CommandExitMessage("No rank with the name '" + name + "'.");
    }

    @Override
    public String argumentDescription() {
        return "rank";
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (Rank rank : Core.getInstance().getRankHandler().getRanks()){
            if (rank.getName().replaceAll(" ", "_").toLowerCase().startsWith(prefix.replaceAll(" ", "_").toLowerCase())) toReturn.add(rank.getName().replaceAll(" ", "_"));
        }
        return toReturn;
    }

}
