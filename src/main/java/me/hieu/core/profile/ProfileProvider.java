package me.hieu.core.profile;

import me.hieu.core.Core;
import me.hieu.libraries.drink.argument.CommandArg;
import me.hieu.libraries.drink.exception.CommandExitMessage;
import me.hieu.libraries.drink.parametric.DrinkProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.IOException;
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

    @javax.annotation.Nullable
    @Override
    public Profile provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        if (name == null) return null;
        try {
            return Core.getInstance().getProfileHandler().getProfileByName(name);
        } catch (Exception e) {
            throw new CommandExitMessage("No profile with the name '" + name + "'.");
        }
    }

    @Override
    public String argumentDescription() {
        return "profile";
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (Profile profile : Core.getInstance().getProfileHandler().getProfiles()){
            if (profile.getName().toLowerCase().startsWith(prefix.toLowerCase())) toReturn.add(profile.getName());
        }
        return toReturn;
    }

}
