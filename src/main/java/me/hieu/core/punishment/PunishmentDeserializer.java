package me.hieu.core.punishment;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 05/10/2024
 */

public class PunishmentDeserializer {

    public static List<Punishment> convert(String string){
        Type punishmentListType = new TypeToken<List<Punishment>>(){}.getType();
        return new Gson().fromJson(string, punishmentListType);
    }

}
