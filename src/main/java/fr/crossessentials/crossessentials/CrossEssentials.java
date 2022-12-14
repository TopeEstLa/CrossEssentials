package fr.crossessentials.crossessentials;

import fr.crossessentials.crossessentials.data.broker.BrokerHelper;
import fr.crossessentials.crossessentials.data.web.WebHelper;
import fr.crossessentials.crossessentials.listeners.LoginListener;
import fr.crossessentials.crossessentials.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrossEssentials extends JavaPlugin {

    static CrossEssentials INSTANCE;
    UserManager userManager;

    public static CrossEssentials getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        userManager = new UserManager();

        // data
        WebHelper.init();
        BrokerHelper.init();

        // listeners & commands
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (userManager != null)
            userManager.shutdown();
        BrokerHelper.shutdown();
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
