package fr.crossessentials.crossessentials.users;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class User {
    UUID owningUUID;


    /**
     * returns the owning player of this User
     * <p>Returns an empty optional if the player is not online</p>
     */
    public Optional<Player> getPlayer() {
        Player owningPlayer = Bukkit.getPlayer(owningUUID);
        return Optional.ofNullable(owningPlayer);
    }

}
