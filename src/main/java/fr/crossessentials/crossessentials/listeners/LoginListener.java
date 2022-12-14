package fr.crossessentials.crossessentials.listeners;

import fr.crossessentials.crossessentials.CrossEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void event(AsyncPlayerPreLoginEvent e){
        CrossEssentials.getInstance().getUserManager().fetchUser(e.getUniqueId());
    }

    @EventHandler
    public void event(PlayerQuitEvent e){
        CrossEssentials.getInstance().getUserManager().removeUser(e.getPlayer().getUniqueId());
    }
}
