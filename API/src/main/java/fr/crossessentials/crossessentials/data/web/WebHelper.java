package fr.crossessentials.crossessentials.data.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.crossessentials.crossessentials.data.web.impl.RestAPI;
import fr.crossessentials.crossessentials.json.JsonObject;
import fr.crossessentials.crossessentials.users.User;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WebHelper {

    static WebAPI api = new RestAPI();
    static ObjectMapper objectMapper;


    public static boolean init() {
        objectMapper = new ObjectMapper();
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
        body.write("uuid", uuid.toString());

        api.makeRequest("/users", "GET", body, webResponse -> {
            Optional<String> message = webResponse.message();
            if (webResponse.isError() || webResponse.isTimeout() || message.isEmpty()) {
                return;
            }

            User user;
            try{
                user = objectMapper.readValue(message.get(), User.class);
            }catch (Exception e){
                System.out.println("Could not deserialize user from json\n"+message.get());
                return;
            }
            response.set(Optional.ofNullable(user));
        });
        return response.get();
    }


}
