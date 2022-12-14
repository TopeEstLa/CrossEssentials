package fr.crossessentials.crossessentials.providers;

import com.google.gson.Gson;

public class GSONProvider {
    static Gson gson = new Gson();


    public static Gson get() {
        return gson;
    }
}
