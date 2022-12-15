package fr.crossessentials.crossessentials;

import fr.crossessentials.crossessentials.data.broker.BrokerHelper;
import fr.crossessentials.crossessentials.data.web.WebHelper;
import fr.crossessentials.crossessentials.listeners.LoginListener;
import fr.crossessentials.crossessentials.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrossEssentials extends JavaPlugin implements EssentialsPlugin {

    UserManager userManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        userManager = new UserManager(this);

        // data
        WebHelper.init(this);
        BrokerHelper.init(this);

        // listeners & commands
        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
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
