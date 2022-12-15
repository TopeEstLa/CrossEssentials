package fr.crossessentials.crossessentials.utils;

import fr.crossessentials.crossessentials.CrossEssentials;
import org.bukkit.scheduler.BukkitRunnable;

public class BukkitExecutor {

    private static CrossEssentials plugin;

    public static void init(CrossEssentials plugin){

        BukkitExecutor.plugin = plugin;
    }
    public static void sync(Runnable runnable){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(plugin);
    }

    public static void sync(Runnable runnable, long delay){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(plugin, delay);
    }

    public static void async(Runnable runnable){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(plugin);
    }

    public static void async(Runnable runnable, long delay){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(plugin, delay);
    }
}
