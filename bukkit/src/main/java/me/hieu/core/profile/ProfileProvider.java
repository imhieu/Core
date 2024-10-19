package me.hieu.core.profile;

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

public class ProfileProvider extends DrinkProvider<Profile> {


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
    public Profile provide(@NotNull CommandArg commandArg, @NotNull List<? extends Annotation> list) throws CommandExitMessage {
        String name = commandArg.get();
        Profile profile = Core.getInstance().getProfileHandler().getProfileByName(name);
        if (profile != null) return profile;
        throw new CommandExitMessage("No profile with the name '" + name + "'.");
    }

    @Override
    public String argumentDescription() {
        return "profile";
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (Profile profile : Core.getInstance().getProfileHandler().getProfiles()){
            if (profile.isDisguised()){
                if (profile.getDisguiseProfile().getName().toLowerCase().startsWith(prefix.toLowerCase())) toReturn.add(profile.getDisguiseProfile().getName());
            } else {
                if (profile.getName().toLowerCase().startsWith(prefix.toLowerCase())) toReturn.add(profile.getName());
            }
        }
        return toReturn;
    }

}
