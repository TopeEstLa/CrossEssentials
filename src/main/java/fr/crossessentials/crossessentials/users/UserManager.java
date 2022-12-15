package fr.crossessentials.crossessentials.users;

import fr.crossessentials.crossessentials.CrossEssentials;
import fr.crossessentials.crossessentials.utils.BukkitExecutor;
import fr.crossessentials.crossessentials.data.web.WebHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserManager {
    Map<UUID, User> loadedUsers = new HashMap<>();
    private final CrossEssentials plugin;

    public UserManager(CrossEssentials crossEssentials) {
        // init

        this.plugin = crossEssentials;
    }


    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(loadedUsers.get(uuid));
    }

    /**
     * Will try and fetch the user
     * <p>This will return the cache version if available</p>
     * @param uuid target uuid
     * @return the user, or null if a timeout or error occured
     */
    public CompletableFuture<Optional<User>> fetchUser(UUID uuid){
        return fetchUser(uuid,false);
    }


    /**
     * Will try and fetch the user from the web api
     * <p>This will return the cache version if available and if ignoreCache is false</p>
     * @param uuid target uuid
     * @return the user, or null if a timeout or error occured
     */
    public CompletableFuture<Optional<User>> fetchUser(UUID uuid, boolean ignoreCache){
        CompletableFuture<Optional<User>> future = new CompletableFuture<>();

        if(!ignoreCache){
            User user = loadedUsers.get(uuid);
            if(user != null)
                future.complete(Optional.of(user));
        }
        BukkitExecutor.async(() -> future.complete(WebHelper.fetchUser(uuid)));
        return future;
    }


    /**
     * removes a user
     * @param uniqueId target uuid
     */
    public void removeUser(UUID uniqueId) {

        loadedUsers.remove(uniqueId);
    }

    public void shutdown() {
        // Nothing for now
    }

}
