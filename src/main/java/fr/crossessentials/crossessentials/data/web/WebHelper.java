package fr.crossessentials.crossessentials.data.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.crossessentials.crossessentials.CrossEssentials;
import fr.crossessentials.crossessentials.data.web.impl.RestAPI;
import fr.crossessentials.crossessentials.providers.GSONProvider;
import fr.crossessentials.crossessentials.users.User;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WebHelper {

    static WebAPI api = new RestAPI();
    private static CrossEssentials plugin;


    public static boolean init(CrossEssentials crossEssentials) {
        WebHelper.plugin = crossEssentials;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        api.makeRequest("","GET",null, webResponse -> {
            if(webResponse.isError() || webResponse.isTimeout()){
                return;
            }
            atomicBoolean.set(true);
        });
        return atomicBoolean.get();
    }

    public static Optional<User> fetchUser(UUID uuid){
        AtomicReference<Optional<User>> response = new AtomicReference<>(Optional.empty());
        JsonObject body = new JsonObject();
        body.add("uuid",new JsonPrimitive(uuid.toString()));

        api.makeRequest("/users", "GET", body, webResponse -> {
            Optional<JsonObject> message = webResponse.message();
            if (webResponse.isError() || webResponse.isTimeout() || message.isEmpty()) {
                return;
            }

            Gson gson = GSONProvider.get();
            User user;
            try{
                user = gson.fromJson(message.get(), User.class);
            }catch (Exception e){
                plugin.getLogger().severe("Could not deserialize user from json\n"+message.get());
                return;
            }
            response.set(Optional.ofNullable(user));
        });
        return response.get();
    }


}
