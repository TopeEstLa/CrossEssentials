package fr.crossessentials.crossessentials.utils;

import fr.crossessentials.crossessentials.CrossEssentials;
import org.bukkit.scheduler.BukkitRunnable;

public class BukkitExecutor {

    public static void sync(Runnable runnable){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(CrossEssentials.getInstance());
    }

    public static void sync(Runnable runnable, long delay){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(CrossEssentials.getInstance(), delay);
    }

    public static void async(Runnable runnable){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(CrossEssentials.getInstance());
    }

    public static void async(Runnable runnable, long delay){
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(CrossEssentials.getInstance(), delay);
    }
}
