package fr.crossessentials.crossessentials.listeners;

import fr.crossessentials.crossessentials.CrossEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

    private final CrossEssentials plugin;

    public LoginListener(CrossEssentials plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void event(AsyncPlayerPreLoginEvent e){
        plugin.getUserManager().fetchUser(e.getUniqueId());
    }

    @EventHandler
    public void event(PlayerQuitEvent e){
        plugin.getUserManager().removeUser(e.getPlayer().getUniqueId());
    }
}
