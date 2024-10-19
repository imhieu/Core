package me.hieu.core.grant;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 05/10/2024
 */

public class GrantDeserializer {

    public static List<Grant> convert(String string){
        Type grantListType = new TypeToken<List<Grant>>(){}.getType();
        return new Gson().fromJson(string, grantListType);
    }

}
